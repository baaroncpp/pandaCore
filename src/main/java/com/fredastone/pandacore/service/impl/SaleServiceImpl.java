package com.fredastone.pandacore.service.impl;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.fredastone.pandacore.azure.IAzureOperations;
import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.constants.ServiceConstants;
import com.fredastone.pandacore.constants.TokenTypes;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.entity.ApprovalReview;
import com.fredastone.pandacore.entity.Approver;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.Lease;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.PayGoProduct;
import com.fredastone.pandacore.entity.Product;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.entity.TotalLeasePayments;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.entity.VLeaseSaleDetails;
import com.fredastone.pandacore.exception.AgentNotFoundException;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.exception.LeaseOfferNotFoundException;
import com.fredastone.pandacore.exception.ProductNotFoundException;
import com.fredastone.pandacore.exception.SaleNotFoundException;
import com.fredastone.pandacore.models.LeaseSale;
import com.fredastone.pandacore.models.Notification;
import com.fredastone.pandacore.models.Notification.NotificationType;
import com.fredastone.pandacore.models.SaleModel;
import com.fredastone.pandacore.repository.AgentMetaRepository;
import com.fredastone.pandacore.repository.ApprovalReviewRepository;
import com.fredastone.pandacore.repository.ApproverRepository;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
import com.fredastone.pandacore.repository.LeaseOfferRepository;
import com.fredastone.pandacore.repository.LeaseRepository;
import com.fredastone.pandacore.repository.LeaseSaleDetailRepository;
import com.fredastone.pandacore.repository.PayGoProductRepository;
import com.fredastone.pandacore.repository.ProductsRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.SaleRollbackRepository;
import com.fredastone.pandacore.repository.TokenRepository;
import com.fredastone.pandacore.repository.TotalLeasePaymentsRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.repository.UserRoleRepository;
import com.fredastone.pandacore.service.ApprovalService;
import com.fredastone.pandacore.service.NotificationService;
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
	
	@Value("${notification.message.leaseapprovednewmessage}")
	private String leaseSaleApprovedMsg;

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
	private LeaseSaleDetailRepository lsdDao;
	private CustomerMetaRepository customerMetaRepository;
	private PayGoProductRepository payGoRepo;
	private UserRoleRepository userRoleRepository;
	private IAzureOperations azureOperations;
	private NotificationService notificationService;
	private ApproverRepository approverDao;
	private ApprovalReviewRepository approvalReviewDao;

	private static final String LEASE_SALE = "Lease";
	private static final String DIRECT_SALE = "Direct";
	private static final String NON_PAYGO_SALE = "Nonpaygo";

	@Autowired
	public SaleServiceImpl(CustomerMetaRepository customerMetaRepository, SaleRollbackRepository rollbackDao,
			SaleRepository saleDao, AgentMetaRepository agentDao, ProductsRepository productDao,
			LeaseOfferRepository leaseOfferDao, LeaseRepository leaseDao, TokenRepository saleTokenDao,
			TotalLeasePaymentsRepository totalLeaseRepayDao, UserRepository userDao, LeaseSaleDetailRepository lsdDao,
			PayGoProductRepository payGoRepo, IAzureOperations azureOperations, NotificationService notificationService,
			UserRoleRepository userRoleRepository, ApproverRepository approverDao, ApprovalReviewRepository approvalReviewDao) {
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
		this.lsdDao = lsdDao;
		this.customerMetaRepository = customerMetaRepository;
		this.payGoRepo = payGoRepo;
		this.azureOperations = azureOperations;
		this.notificationService = notificationService;
		this.userRoleRepository = userRoleRepository;
		this.approverDao = approverDao;
		this.approvalReviewDao = approvalReviewDao;
	}
	
	public Sale noPaygoSale(Sale sale) {
		
		// Retrieve the product that is being sold
		// Optional<Product> product = productDao.findById(sale.getProductid());
		Optional<Product> product = productDao.findById(sale.getProductid());
		if (!product.isPresent() || !product.get().getIsActive()) {
			throw new ProductNotFoundException(sale.getProductid());
		}

		// Get the agent making this sale
		Optional<AgentMeta> agent = agentDao.findById(sale.getAgentid());
		if (!agent.isPresent() || !agent.get().isIsactive() || agent.get().isIsdeactivated()) {
			throw new AgentNotFoundException(sale.getAgentid()+ " NON AGENT");
		}

		final float agentCommissionRate = (float) agent.get().getAgentcommissionrate() / 100;

		// Get the total amount to be paid
		float totalAmount = product.get().getUnitcostselling() * sale.getQuantity();
		float agentCommission = totalAmount * agentCommissionRate;

		sale.setAgentcommission(agentCommission);
		sale.setAmount(totalAmount);
		sale.setSalestatus((short) ServiceConstants.PENDING_APPROVAL);
		sale.setProductid(product.get().getId());

		sale.setId(ServiceUtils.getUUID());
		sale.setSaletype(DIRECT_SALE);
		sale.setIsreviewed(Boolean.TRUE);
		sale.setSalestatus((short) ServiceConstants.ACCEPTED_APPROVAL);
		sale.setCompletedon(new Date());

		saleDao.save(sale);
		
		return sale;
	}

	@Transactional
	@Override
	public Sale recoredNewDirectSale(Sale sale) {

		// Retrieve the product that is being sold
		// Optional<Product> product = productDao.findById(sale.getProductid());
		Optional<Product> product = productDao.findById(sale.getProductid());
		if (!product.isPresent() || !product.get().getIsActive()) {
			throw new ProductNotFoundException(sale.getProductid());
		}
		
		Optional<PayGoProduct> payGoProduct = payGoRepo.findById(sale.getScannedserial());
		if (!payGoProduct.isPresent()) {
			throw new ItemNotFoundException(sale.getScannedserial());
		}

		if (payGoProduct.get().getPayGoProductStatus().name().equals(PayGoProductStatus.PENDING.name())) {
			throw new RuntimeException("PayGo product is sold, Pending approval");
		}

		if (payGoProduct.get().getPayGoProductStatus().name().equals(PayGoProductStatus.SOLD.name())) {
			throw new RuntimeException("PayGo product is already sold");
		}

		// Get the agent making this sale
		Optional<AgentMeta> agent = agentDao.findById(sale.getAgentid());

		if (!agent.isPresent() || !agent.get().isIsactive() || agent.get().isIsdeactivated()) {
			throw new AgentNotFoundException(sale.getAgentid()+ " NON AGENT");
		}

		final float agentCommissionRate = (float) agent.get().getAgentcommissionrate() / 100;

		// Get the total amount to be paid
		float totalAmount = product.get().getUnitcostselling() * sale.getQuantity();
		float agentCommission = totalAmount * agentCommissionRate;

		sale.setAgentcommission(agentCommission);
		sale.setAmount(totalAmount);
		sale.setSalestatus((short) ServiceConstants.PENDING_APPROVAL);
		sale.setProductid(product.get().getId());

		sale.setId(ServiceUtils.getUUID());
		sale.setSaletype(DIRECT_SALE);

		saleDao.save(sale);
		
		payGoProduct.get().setPayGoProductStatus(PayGoProductStatus.PENDING);
		payGoRepo.save(payGoProduct.get());

		//notificationService.approveSaleNotification(sale);

		return sale;
	}

	@Transactional
	@Override
	public LeaseSale recoredNewLeaseSale(int leaseOfferId, String agentid, String customerid, float cord_lat,
			float cord_long, String scannedserial) {

		Optional<LeaseOffer> leaseOffer = leaseOfferDao.findById(leaseOfferId);

		if (!leaseOffer.isPresent() || !leaseOffer.get().isIsactive()) {
			throw new LeaseOfferNotFoundException();
		}

		final Product product = leaseOffer.get().getProduct();

		// Retrieve the product that is being sold
		if (!product.getIsActive()) {
			throw new ProductNotFoundException(product.getId());
		}

		Optional<PayGoProduct> payGoProduct = payGoRepo.findById(scannedserial);
		if (!payGoProduct.isPresent()) {
			throw new ItemNotFoundException(scannedserial);
		}

		if (payGoProduct.get().getPayGoProductStatus().name().equals(PayGoProductStatus.PENDING.name())) {
			throw new RuntimeException("PayGo product is sold, Pending approval");
		}

		if (payGoProduct.get().getPayGoProductStatus().name().equals(PayGoProductStatus.SOLD.name())) {
			throw new RuntimeException("PayGo product is already sold");
		}

		// Get the agent or employee making this sale
		Optional<AgentMeta> agent = agentDao.findById(agentid);
		//Optional<User> saleUser = userDao.findById(agentid);

		/*
		 * if (!saleUser.isPresent() ||
		 * !saleUser.get().getUsertype().equals(UserType.AGENT.name()) ||
		 * !saleUser.get().getUsertype().equals(UserType.EMPLOYEE.name()) ||
		 * !saleUser.get().isIsactive()) { throw new
		 * AgentNotFoundException(saleUser.get().getId()); }
		 */
		
		if (!agent.isPresent() || !agent.get().isIsactive() || agent.get().isIsdeactivated()) {
			throw new AgentNotFoundException(agent.get().getUserid()+ " NON AGENT"); 
		}
				
		Optional<CustomerMeta> customerMeta = customerMetaRepository.findById(customerid);
		

		if (!customerMeta.isPresent()) {
			throw new RuntimeException("Customer of ID :" + customerid + " does not exist");
		}

		// For lease payments .. important are
		// initial deposit
		// coupon if any -- not processing this right now
		// unit cost
		// percent markup
		// lease period

		final float agentCommissionRate = (float) agent.get().getAgentcommissionrate() / 100;

		// Get the total amount to be paid
		final float agentCommission = product.getUnitcostselling() * agentCommissionRate;

		// Get mark up on sale
		final float markup = product.getUnitcostselling() * ((float) leaseOffer.get().getPercentlease() / 100);

		float totalAmount = product.getUnitcostselling() + markup;

		// Get the daily payment to be made
		final float dailypayment = (float) (totalAmount - leaseOffer.get().getIntialdeposit())
				/ leaseOffer.get().getLeaseperiod();

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
		lease.setExpectedfinishdate(Date.from(LocalDate.now().plusDays(leaseOffer.get().getLeaseperiod())
				.atStartOfDay(ZoneId.systemDefault()).toInstant()));

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

		payGoProduct.get().setPayGoProductStatus(PayGoProductStatus.PENDING);
		payGoRepo.save(payGoProduct.get());

		notificationService.approveSaleNotification(sale);

		return new LeaseSale(sale, lease);

	}

	@Override
	public Sale rollbackSale(String saleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Sale completeSale(String saleId, String approverId) {

		// Locate the sale that should be completed and verify that is in pending state
		Optional<Sale> sale = saleDao.findById(saleId);
		final TokenOperation tokenService = new TokenOperation();
		
		Optional<CustomerMeta> customer = customerMetaRepository.findById(sale.get().getCustomerid());
		if(!customer.isPresent()) {
			throw new RuntimeException("Customer Not Found");
		}
		
		User customerUser = customer.get().getUser();
		
		if(!customerUser.isIsactive() || !customerUser.isIsapproved()) {
			customerUser.setIsactive(Boolean.TRUE);
			customerUser.setIsapproved(Boolean.TRUE);
			
			final ApprovalReview review = ApprovalReview.builder()
					.createdon(new Date())
					.itemid(customerUser.getId())
					.reviewtype(2)
					.review("Approved").build();	
			
			final Approver approver = Approver.builder()
					.id(ServiceUtils.getUUID())
					.createdon(new Date())
					.userid(approverId)
					.itemapproved("customer")
					.itemid(customerUser.getId()).build();
			
			review.setId(ServiceUtils.getUUID());
			approvalReviewDao.save(review);
			approverDao.save(approver);
		
			
			customer.get().setUser(customerUser);
			customerMetaRepository.save(customer.get());
		}
		//
		if (!sale.isPresent() || sale.get().getSalestatus() != ServiceConstants.PENDING_APPROVAL) {
			throw new SaleNotFoundException(saleId);
		}

		final Optional<User> user = userDao.findById(sale.get().getCustomerid());
		// Sale is availabe and in pending state
		sale.get().setSalestatus((short) ServiceConstants.ACCEPTED_APPROVAL);
		sale.get().setCompletedon(new Date());
		sale.get().setIsreviewed(Boolean.TRUE);

		Token saleToken = new Token();
		saleToken.setLeasepaymentid(sale.get().getId());
		// Generate full unlock for that device
		// Check if its a direct sale and unlock the device
		if (sale.get().getSaletype().equals(DIRECT_SALE)) {

			// TODO: Give this to the queue to send email and SMS message to customer
			final String token = tokenService.generateGeneralPurposeToken(sale.get().getScannedserial(),
					CommandNames.CLEAR_LOAN, 1);

			saleToken.setToken(token);
			saleToken.setType(TokenTypes.OPEN);
			saleTokenDao.save(saleToken);

			final Sale s = saleDao.save(sale.get());

			// Notify the user

			if (isSendDirectTokenMsg.equals("true")) {
				final Notification notificaton = Notification.builder().type(NotificationType.SMS)
						.address(user.get().getPrimaryphone()).content(String.format(directSaleTokenMsg, token))
						.build();

				rabbitTemplate.convertAndSend(notificationExchange, smsRoutingKey, notificaton.toString());

				// Move this to private m
				if (user.get().getEmail() != null && !user.get().getEmail().isEmpty()) {
					notificaton.setType(NotificationType.EMAIL);
					notificaton.setSubject(directSaleTokenMsgSubj);
					notificaton.setAddress(user.get().getEmail());

					rabbitTemplate.convertAndSend(notificationExchange, emailRoutingKey, notificaton.toString());

				}

			}
			
			System.out.println("Direct sale: "+token);

			//notificationService.approvedSaleNotification(s);
			return s;
		}
		// set product status to sold
		Optional<PayGoProduct> payGo = payGoRepo.findById(sale.get().getScannedserial());
		if (!payGo.isPresent()) {
			throw new RuntimeException("PayGo with serial number " + sale.get().getScannedserial() + " does not exist");
		}

		PayGoProduct prod = payGo.get();

		if (prod.getPayGoProductStatus() != PayGoProductStatus.SOLD) {
			prod.setPayGoProductStatus(PayGoProductStatus.SOLD);
			payGoRepo.save(prod);
		} else {
			throw new RuntimeException("PayGo with serial number " + prod.getTokenSerialNumber() + " is already sold");
		}

		if (sale.get().isIsreviewed() == Boolean.FALSE) {
			throw new RuntimeException("Transaction has not been reviewed");
		}
		// Process Leased tickets
		final Optional<Lease> lease = leaseDao.findById(sale.get().getId());

		if (!lease.isPresent() || lease.get().isIsactivated() == Boolean.TRUE) {
			throw new SaleNotFoundException(saleId);
		}

		lease.get().setIsactivated(Boolean.TRUE);

		// Generate first token, 1 day token
		final String token = tokenService.generatePaymentToken(sale.get().getScannedserial(), 1,
				String.valueOf(leaseFirstTokenDays));

		/*
		 * saleToken.setType(TokenTypes.PAY); saleToken.setTimes(1);
		 * saleToken.setDays(leaseFirstTokenDays); saleToken.setToken(token);
		 */

		TotalLeasePayments totalLeasePayments = new TotalLeasePayments();
		totalLeasePayments.setId(ServiceUtils.getUUID());
		totalLeasePayments.setTimes(1);
		totalLeasePayments.setLeaseid(lease.get().getId());
		totalLeasePayments.setNextpaymentdate(new Date());
		totalLeasePayments.setTotalamountpaid(0);
		//totalLeasePayments.setTotalamountowed(lease.get().getTotalleasevalue() - lease.get().getInitialdeposit());
		totalLeasePayments.setTotalamountowed(lease.get().getTotalleasevalue());

		//saleTokenDao.save(saleToken);
		leaseDao.save(lease.get());
		totalLeaseRepayDao.save(totalLeasePayments);

		final Sale s = saleDao.save(sale.get());

		// Notify the user
		/*final Notification notificaton = Notification.builder().type(NotificationType.SMS)
				.address(user.get().getPrimaryphone())
				.content(String.format(leaseTokenNewMsg, user.get().getFirstname(), user.get().getLastname())).build();
*/
		final Notification notificaton = Notification.builder().type(NotificationType.SMS)
				.address(user.get().getPrimaryphone())
				.content(String.format(leaseSaleApprovedMsg, user.get().getFirstname(), user.get().getLastname())).build();

		
		rabbitTemplate.convertAndSend(notificationExchange, smsRoutingKey, notificaton.toString());

		// Move this to private method
		if (user.get().getEmail() != null && !user.get().getEmail().isEmpty()) {
			notificaton.setType(NotificationType.EMAIL);
			notificaton.setSubject(directSaleTokenMsgSubj);
			notificaton.setAddress(user.get().getEmail());

			rabbitTemplate.convertAndSend(notificationExchange, emailRoutingKey, notificaton.toString());
		}

		notificationService.approvedSaleNotification(s);
		return s;
	}

	@Override
	public List<SaleModel> getAllSales(int page, int size, String sortby, Direction sortOrder) {

		List<SaleModel> saleModels = new ArrayList<>();

		final Pageable pageRequest = PageRequest.of(page, size, Sort.by(Direction.DESC, sortby));
		Page<Sale> allsorted = saleDao.findAll(pageRequest);

		List<Sale> sales = allsorted.getContent();

		for (Sale object : sales) {
			saleModels.add(convertToSaleModel(object));
		}

		return saleModels;
	}

	@Override
	public Page<Sale> getVerifiedLeaseSale(String agentId, int page, int count, String sortBy, Direction sortOrder) {

		final Pageable pageRequest = PageRequest.of(page, count, Sort.by(sortOrder, sortBy));
		Page<Sale> allsorted = saleDao.findAllVerified(agentId, "Lease", pageRequest);
		return allsorted;
	}

	@Override
	public Page<Sale> getUnverifiedleaseSale(String agentId, int page, int count, String sortBy, Direction orderBy) {

		final Pageable pageRequest = PageRequest.of(page, count, Sort.by(orderBy, sortBy));
		Page<Sale> allsorted = saleDao.findAllUnverified(agentId, "Lease", pageRequest);
		return allsorted;
	}

	@Override
	public Page<VLeaseSaleDetails> getAllLeaseSaleByReviewStatus(boolean reviewStatus, int page, int count,
			String sortby, Direction orderby) {

		final Pageable pageRequest = PageRequest.of(page, count, Sort.by(orderby, sortby));
		Page<VLeaseSaleDetails> reviewsales = lsdDao.findByisreviewed(reviewStatus, pageRequest);
		return reviewsales;
	}

	@Override
	public VLeaseSaleDetails getLeaseSaleDetail(String saleid) {
		Optional<VLeaseSaleDetails> d = lsdDao.findById(saleid);
		return d.isPresent() == false ? null : d.get();
	}

	@Override
	public Page<VLeaseSaleDetails> getAllLeaseSaleDetail(int page, int count, String sortby, Direction orderby) {

		final Pageable pageRequest = PageRequest.of(page, count, Sort.by(orderby, sortby));
		Page<VLeaseSaleDetails> reviewsales = lsdDao.findAll(pageRequest);
		return reviewsales;
	}

	@Override
	public List<SaleModel> getAllSalesByAgentId(String agentid, int page, int count, String sortby, Direction orderby) {
		List<SaleModel> saleModels = new ArrayList<>();
		final Pageable pageRequest = PageRequest.of(page, count, Sort.by(orderby, sortby));
		Page<Sale> sales = saleDao.findAllSaleByAgentId(agentid, pageRequest);

		List<Sale> salesList = sales.getContent();

		for (Sale object : salesList) {
			saleModels.add(convertToSaleModel(object));
		}

		return saleModels;
	}

	@Override
	public Map<String, Integer> getCustomerSaleSums(String customerId) {

		Optional<User> user = userDao.findById(customerId);
		if (!user.isPresent()) {
			throw new RuntimeException("Agent does not exist");
		}

		Map<String, Integer> result = new HashMap<>();

		List<Sale> directSales = saleDao.findAllByCustomeridAndSaletype(customerId, "Direct");

		if (!directSales.isEmpty()) {
			result.put("DIRECT", directSales.size());
		} else {
			result.put("DIRECT", 0);
		}

		List<Sale> leaseSales = saleDao.findAllByCustomeridAndSaletype(customerId, "Lease");

		if (!leaseSales.isEmpty()) {
			result.put("LEASE", leaseSales.size());
		} else {
			result.put("LEASE", 0);
		}

		return result;
	}

	@Override
	public Map<String, Integer> getAgentSaleSums(String agentId) {

		int leaseSale = 0;
		int directSale = 0;

		Optional<User> user = userDao.findById(agentId);
		if (!user.isPresent()) {
			throw new RuntimeException("Agent does not exist");
		}

		List<Sale> sales = saleDao.findAllByAgentid(agentId);

		Map<String, Integer> result = new HashMap<>();

		List<Sale> directSales = saleDao.findAllByAgentidAndSaletype(agentId, "Direct");

		if (!directSales.isEmpty()) {
			result.put("DIRECT", directSales.size());
		} else {
			result.put("DIRECT", 0);
		}

		List<Sale> leaseSales = saleDao.findAllByAgentidAndSaletype(agentId, "Lease");

		if (!leaseSales.isEmpty()) {
			result.put("LEASE", leaseSales.size());
		} else {
			result.put("LEASE", 0);
		}

		return result;
	}

	public SaleModel convertToSaleModel(Sale sale) {

		SaleModel saleModel = new SaleModel();

		Optional<AgentMeta> agent = agentDao.findById(sale.getAgentid());
		if (!agent.isPresent()) {
			throw new ItemNotFoundException(sale.getAgentid());
		}

		try {
			agent.get().setProfilepath(azureOperations.uploadProfile(sale.getAgentid()));
		} catch (InvalidKeyException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Optional<CustomerMeta> customer = customerMetaRepository.findById(sale.getCustomerid());
		if (!customer.isPresent()) {
			throw new ItemNotFoundException(sale.getCustomerid());
		}

		try {
			customer.get().setProfilephotopath(azureOperations.uploadProfile(sale.getCustomerid()));
		} catch (InvalidKeyException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Optional<Product> product = productDao.findById(sale.getProductid());
		if (!product.isPresent()) {
			throw new ItemNotFoundException(sale.getProductid());
		}

		saleModel.setAgent(agent.get());
		saleModel.setCustomer(customer.get());
		saleModel.setProduct(product.get());
		saleModel.setAgentcommission(sale.getAgentcommission());
		saleModel.setAmount(sale.getAmount());
		saleModel.setCompletedon(sale.getCompletedon());
		saleModel.setCreatedon(sale.getCreatedon());
		saleModel.setDescription(sale.getDescription());
		saleModel.setId(sale.getId());
		saleModel.setIsreviewed(sale.isIsreviewed());
		saleModel.setLat(sale.getLat());
		saleModel.setLong_(sale.getLong_());
		saleModel.setQuantity(sale.getQuantity());
		saleModel.setSalestatus(sale.getSalestatus());
		saleModel.setSaletype(sale.getSaletype());
		saleModel.setScannedserial(sale.getScannedserial());

		return saleModel;
	}

	@Override
	public List<SaleModel> mobileUserGetSales(String userId, int page, int count, String sortBy, Direction orderBy) {

		Optional<User> user = userDao.findById(userId);
		// List<SaleModel> result = new ArrayList<>();

		if (!user.isPresent()) {
			throw new RuntimeException("user not found");
		}

		List<UserRole> userRoles = userRoleRepository.findAllByUser(user.get());

		String userType = user.get().getUsertype();

		if (userType.equals(UserType.EMPLOYEE.name())) {

			for (UserRole object : userRoles) {
				if (object.getRole().getName().equals(RoleName.ROLE_MANAGER)
						|| object.getRole().getName().equals(RoleName.ROLE_SENIOR_MANAGER)
						|| object.getRole().getName().equals(RoleName.ROLE_MARKETING)
						|| object.getRole().getName().equals(RoleName.ROLE_FINANCE)) {
					return getAllSales(page, count, sortBy, orderBy);
				} else {
					throw new RuntimeException("Permission denied, contact Admin");
				}
			}

		} else if (userType.equals(UserType.AGENT.name())) {
			return getAllSalesByAgentId(userId, page, count, sortBy, orderBy);
		}
		// TODO Auto-generated method stub
		return null;
	}

}
