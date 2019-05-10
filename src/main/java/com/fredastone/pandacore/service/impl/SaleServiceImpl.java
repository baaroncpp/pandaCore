package com.fredastone.pandacore.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.constants.ServiceConstants;
import com.fredastone.pandacore.constants.TokenTypes;
import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.entity.Lease;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.Product;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.entity.TotalLeasePayments;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.exception.AgentNotFoundException;
import com.fredastone.pandacore.exception.LeaseOfferNotFoundException;
import com.fredastone.pandacore.exception.ProductNotFoundException;
import com.fredastone.pandacore.exception.SaleNotFoundException;
import com.fredastone.pandacore.models.LeaseSale;
import com.fredastone.pandacore.models.Notification;
import com.fredastone.pandacore.models.Notification.NotificationType;
import com.fredastone.pandacore.repository.AgentMetaRepository;
import com.fredastone.pandacore.repository.LeaseOfferRepository;
import com.fredastone.pandacore.repository.LeaseRepository;
import com.fredastone.pandacore.repository.ProductsRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.SaleRollbackRepository;
import com.fredastone.pandacore.repository.TokenRepository;
import com.fredastone.pandacore.repository.TotalLeasePaymentsRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.SaleService;
import com.fredastone.pandacore.util.ServiceUtils;
import com.fredastone.pandasolar.token.CommandNames;
import com.fredastone.pandasolar.token.TokenOperation;

@Transactional
@Service
public class SaleServiceImpl implements SaleService {
	
	@Value("${notification.exchange.name}")
	private String notificationExchange;

	@Value("${notification.queue.email.name}")
	private String emailQueue;

	@Value("${notification.queue.sms.name}")
	private String smsQueue;

	@Value("${notification.routing.sms.key}")
	private String smsRoutingKey;


	@Value("${notification.routing.email.key}")
	private String emailRoutingKey;
	
	@Value("${notification.message.newdirecttoken}")
	private String directSaleTokenMsg;
	
	@Value("${notification.message.newtoken.send}")
	private String isSendDirectTokenMsg;
	
	@Value("${notification.message.newtokensubject}")
	private String directSaleTokenMsgSubj;
	
	@Value("${notification.message.leasetokennewmessage}")
	private String leaseTokenNewMsg;
	
	@Value("${notification.message.leasetokensubject}")
	private String leaseTokenNewSubj;
	
	@Value("${leasefirsttokendays}")
	private int leaseFirstTokenDays;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	private SaleRollbackRepository rollbackDao;
	private SaleRepository saleDao;
	private ProductsRepository productDao;
	private AgentMetaRepository agentDao;
	private LeaseOfferRepository leaseOfferDao;
	private LeaseRepository leaseDao;
	private TokenRepository saleTokenDao;
	private TotalLeasePaymentsRepository totalLeaseRepayDao;
	private UserRepository userDao;
	
	private static final String LEASE_SALE = "Lease";
	private static final String DIRECT_SALE = "Direct";
	
	@Autowired
	public SaleServiceImpl(SaleRollbackRepository rollbackDao,SaleRepository saleDao,AgentMetaRepository agentDao,
			ProductsRepository productDao,LeaseOfferRepository leaseOfferDao,LeaseRepository leaseDao,
			TokenRepository saleTokenDao,TotalLeasePaymentsRepository totalLeaseRepayDao,UserRepository userDao) {
		// TODO Auto-generated constructor stub
		this.rollbackDao = rollbackDao;
		this.saleDao = saleDao;
		this.agentDao = agentDao;
		this.productDao = productDao;
		this.leaseOfferDao = leaseOfferDao;
		this.leaseDao = leaseDao;
		this.saleTokenDao = saleTokenDao;
		this.totalLeaseRepayDao = totalLeaseRepayDao;
		this.userDao = userDao;
		
	}
	
	@Transactional
	@Override
	public Sale recoredNewDirectSale(Sale sale) {
		
		//Retrieve the product that is being sold
		Optional<Product> product = productDao.findById(sale.getProductid());
		if(!product.isPresent() || !product.get().getIsActive()) {
			throw new ProductNotFoundException(sale.getProductid());
		}
		
		
		//Get the agent making this sale
		Optional<AgentMeta> agent = agentDao.findById(sale.getAgentid());
		
		if(!agent.isPresent() || !agent.get().isIsactive() || agent.get().isIsdeactivated()) {
			throw new AgentNotFoundException(sale.getAgentid());
		}
		
		final float agentCommissionRate = (float)agent.get().getAgentcommissionrate()/100;
		
		//Get the total amount to be paid
		float totalAmount = product.get().getUnitcostselling()*sale.getQuantity();
		float agentCommission = totalAmount * agentCommissionRate;
		
		sale.setAgentcommission(agentCommission);
		sale.setAmount(totalAmount);
		sale.setSalestatus((short) ServiceConstants.PENDING_APPROVAL);
		
		sale.setId(ServiceUtils.getUUID());
		sale.setSaletype(DIRECT_SALE);
		
		
		return saleDao.save(sale);
	}


	
	@Transactional
	@Override
	public LeaseSale recoredNewLeaseSale(int leaseOfferId,String agentid,String customerid,float cord_lat,float cord_long,String scannedserial) {
		
		Optional<LeaseOffer> leaseOffer = leaseOfferDao.findById(leaseOfferId);
		
		if(!leaseOffer.isPresent() || !leaseOffer.get().isIsactive()) {
			throw new LeaseOfferNotFoundException();
		}
		
		final Product product = leaseOffer.get().getProduct();
		
		
		
		//Retrieve the product that is being sold
		if(!product.getIsActive()) {
			throw new ProductNotFoundException(product.getId());
		}
		
		
		//Get the agent making this sale
		Optional<AgentMeta> agent = agentDao.findById(agentid);
		
		if(!agent.isPresent() || !agent.get().isIsactive() || agent.get().isIsdeactivated()) {
			throw new AgentNotFoundException(agent.get().getUserid());
		}
		
		//For lease payments .. important are 
		// initial deposit
		// coupon if any -- not processing this right now
		// unit cost
		// percent markup
		// lease period
		
		final float agentCommissionRate = (float)agent.get().getAgentcommissionrate()/100;
		
		//Get the total amount to be paid
		final float agentCommission = product.getUnitcostselling() * agentCommissionRate;
		
		//Get mark up on sale
		final float markup = product.getUnitcostselling() * ((float)leaseOffer.get().getPercentlease()/100);

		float totalAmount = product.getUnitcostselling() + markup;
		
		//Get the daily payment to be made
		final float dailypayment = (float)(totalAmount-leaseOffer.get().getIntialdeposit())/leaseOffer.get().getLeaseperiod();
		
		final String id = ServiceUtils.getUUID();
		
		Lease lease = new Lease();
		lease.setId(id);
		lease.setCustomerid(customerid);
		lease.setDailypayment(dailypayment);
		lease.setInitialdeposit(leaseOffer.get().getIntialdeposit());
		lease.setIscompleted(Boolean.FALSE);
		lease.setLeaseOffer(leaseOffer.get());
		lease.setSaleagentid(agentid);
		lease.setTotalleaseperiod(leaseOffer.get().getLeaseperiod());
		lease.setTotalleasevalue(totalAmount);
		lease.setExpectedfinishdate(Date.from(LocalDate.now().plusDays(leaseOffer.get().
				getLeaseperiod()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
	
		Sale sale = new Sale();
		
		sale.setAgentcommission(agentCommission);
		sale.setAmount(totalAmount);
		sale.setSalestatus((short) ServiceConstants.PENDING_APPROVAL);
		
		sale.setId(id);
		sale.setSaletype(LEASE_SALE);
		sale.setAgentid(agentid);
		sale.setCustomerid(customerid);
		sale.setQuantity(1);
		sale.setLat(cord_lat);
		sale.setLong_(cord_long);
		sale.setScannedserial(scannedserial);
		sale.setProductid(lease.getLeaseOffer().getProduct().getId());
		
		
		sale = saleDao.save(sale);
		lease = leaseDao.save(lease);
		
		return new LeaseSale(sale, lease);
	
	}


	@Override
	public Sale rollbackSale(String saleId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Transactional
	@Override
	public Sale completeSale(String saleId) {
		
		//Locate the sale that should be completed and verify that is in pending state
		Optional<Sale> sale  = saleDao.findById(saleId);
		final TokenOperation tokenService = new TokenOperation();
		
		if(!sale.isPresent() || sale.get().getSalestatus() != ServiceConstants.PENDING_APPROVAL) {
			throw new SaleNotFoundException(saleId);
		}
		
		final Optional<User> user = userDao.findById(sale.get().getCustomerid());
		//Sale is availabe and in pending state
		sale.get().setSalestatus((short) ServiceConstants.ACCEPTED_APPROVAL);
		sale.get().setCompletedon(new Date());
		
		Token saleToken  = new Token();
		saleToken.setLeasepaymentid(sale.get().getId());
		//Generate full unlock for that device
		//Check if its a direct sale and unlock the device
		if(sale.get().getSaletype().equals(DIRECT_SALE)) {
			
			//TODO: Give this to the queue to send email and SMS message to customer
			final String token = tokenService.generateGeneralPurposeToken(sale.get().getScannedserial(), CommandNames.CLEAR_LOAN, 1);
			
			saleToken.setToken(token);
			saleToken.setType(TokenTypes.OPEN);
			saleTokenDao.save(saleToken);
			
			final Sale s =  saleDao.save(sale.get());
			
			
			// Notify the user
			
			if(isSendDirectTokenMsg.equals("true")) {
				final Notification notificaton = Notification.builder().type(NotificationType.SMS).address(user.get()
						.getPrimaryphone()).content(String.format(directSaleTokenMsg, token)).build();
	
				rabbitTemplate.convertAndSend(notificationExchange,smsRoutingKey,notificaton.toString());
				
			//Move this to private m
			if(user.get().getEmail() != null && !user.get().getEmail().isEmpty()) {
				notificaton.setType(NotificationType.EMAIL);
				notificaton.setSubject(directSaleTokenMsgSubj);
				notificaton.setAddress(user.get().getEmail());
				
					rabbitTemplate.convertAndSend(notificationExchange,emailRoutingKey,notificaton.toString());
				}
				
			}
			
			return s;
			
		}
		
		//Process Leased tickets
		final Optional<Lease> lease = leaseDao.findById(sale.get().getId());
		
		if(!lease.isPresent() || lease.get().isIsactivated() == Boolean.TRUE) {
			throw new SaleNotFoundException(saleId);
		}
		
		
		lease.get().setIsactivated(Boolean.TRUE);
		
		//Generate first token, 1 day token
		final String token = tokenService.generatePaymentToken(sale.get().getScannedserial(), 1, String.valueOf(leaseFirstTokenDays));	
		
		saleToken.setType(TokenTypes.PAY);
		saleToken.setTimes(1);
		saleToken.setDays(leaseFirstTokenDays);
		saleToken.setToken(token);
		
		TotalLeasePayments totalLeasePayments = new TotalLeasePayments();
		totalLeasePayments.setId(ServiceUtils.getUUID());
		totalLeasePayments.setTimes(1);
		totalLeasePayments.setLeaseid(lease.get().getId());
		totalLeasePayments.setNextpaymentdate(new Date());
		totalLeasePayments.setTotalamountpaid(0);
		totalLeasePayments.setTotalamountowed(lease.get().getTotalleasevalue() - lease.get().getInitialdeposit());
		
		saleTokenDao.save(saleToken);
		leaseDao.save(lease.get());
		totalLeaseRepayDao.save(totalLeasePayments);
			
		final Sale s = saleDao.save(sale.get());
		
		//Notify the user
		final Notification notificaton = Notification.builder().type(NotificationType.SMS).address(user.get()
				.getPrimaryphone()).content(String.format(leaseTokenNewMsg, user.get().getFirstname(),user.get().getLastname())).build();

		rabbitTemplate.convertAndSend(notificationExchange,smsRoutingKey,notificaton.toString());
		
		//Move this to private method
		if(user.get().getEmail() != null && !user.get().getEmail().isEmpty()) {
			notificaton.setType(NotificationType.EMAIL);
			notificaton.setSubject(directSaleTokenMsgSubj);
			notificaton.setAddress(user.get().getEmail());
			
				rabbitTemplate.convertAndSend(notificationExchange,emailRoutingKey,notificaton.toString());
		}
		
		return s;
	}

	@Override
	public Page<Sale> getAllSales(int page, int size, String sortby, Direction sortOrder) {
	
		final Pageable pageRequest = PageRequest.of(page, size,Sort.by(Direction.DESC,sortby));
		Page<Sale> allsorted = saleDao.findAll(pageRequest);
	
		return allsorted;
	}

	@Override
	public Page<Sale> getVerifiedLeaseSale(String agentId, int page, int count, String sortBy, Direction sortOrder) {
		
		final Pageable pageRequest = PageRequest.of(page, count,Sort.by(sortOrder,sortBy));
		Page<Sale> allsorted = saleDao.findAllVerified(agentId, "Lease", pageRequest);
		return allsorted;
	}

	@Override
	public Page<Sale> getUnverifiedleaseSale(String agentId, int page, int count, String sortBy, Direction orderBy) {
		
		final Pageable pageRequest = PageRequest.of(page, count,Sort.by(orderBy,sortBy));
		Page<Sale> allsorted = saleDao.findAllUnverified(agentId, "Lease", pageRequest);
		return allsorted;
	}

}
