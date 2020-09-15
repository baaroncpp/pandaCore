package com.fredastone.pandacore.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.PayGoProduct;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.VCustomerFinanceInfo;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.models.BuyToken;
import com.fredastone.pandacore.models.Notification;
import com.fredastone.pandacore.models.Notification.NotificationType;
import com.fredastone.pandacore.repository.BuyTokenRepository;
import com.fredastone.pandacore.repository.CustomerFinanceInfoRepository;
import com.fredastone.pandacore.repository.LeasePaymentRepository;
import com.fredastone.pandacore.repository.PayGoProductRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.TokenRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.TokenService;
import com.fredastone.pandasolar.token.CommandNames;
import com.fredastone.pandasolar.token.TokenOperation;

@Service
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	private RabbitTemplate rabbitTemplate; 
	
	@Value("${notification.sms.tokenresend.notification}")
	private String smsResendMessage;
	
	@Value("${notification.exchange.name}")
	private String notificationExchange;
	
	@Value("${notification.queue.sms.name}")
	private String smsQueue;

	@Value("${notification.routing.sms.key}")
	private String smsRoutingKey;

	private BuyTokenRepository buyTokenRepo;
	private CustomerFinanceInfoRepository customerFinanceDao;
	private TokenRepository tokenRepository;
	private SaleRepository saleRepository;
	private LeasePaymentRepository leasePaymentRepository;
	private TokenOperation tokenOperation;
	private PayGoProductRepository payGoProductRepo;
	private UserRepository userRepository;
 	
	@Autowired
	public TokenServiceImpl(PayGoProductRepository payGoProductRepo, LeasePaymentRepository leasePaymentRepository, SaleRepository saleRepository, TokenRepository tokenRepository, 
			CustomerFinanceInfoRepository customerfinanceDao, BuyTokenRepository buyTokenRepo, UserRepository userRepository) {
		// TODO Auto-generated constructor stub

		this.customerFinanceDao = customerfinanceDao;
		this.buyTokenRepo = buyTokenRepo;
		this.tokenRepository = tokenRepository;
		this.saleRepository = saleRepository;
		this.leasePaymentRepository = leasePaymentRepository;
		this.payGoProductRepo = payGoProductRepo;
		this.userRepository = userRepository;
	}

	@Override
	public Token createToken(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token getToken(String tokenId) {
		
		return null;
	}

	@Override
	public Token getTokenByPaymentReference(String paymentReference) {
		// TODO Auto-generated method stub
		Optional<Token> token = tokenRepository.findByleasepaymentid(paymentReference);
		if(!token.isPresent()) {
			throw new ItemNotFoundException(paymentReference);
		}
		
		return token.get();
	}

	@Override
	public Token invalidateToken(String token) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Token resendToken(String paymentReference, String userid) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(userid);
		if(!user.isPresent()) {
			throw new RuntimeException("user not found");
		}
		
		if(!user.get().getUsertype().equals(UserType.EMPLOYEE.name())) {
			throw new RuntimeException("Not Authorized");
		}
		
		Optional<Token> token = tokenRepository.findByleasepaymentid(paymentReference);
		if(!token.isPresent()) {
			throw new RuntimeException("Token not found");
		}
		
		Optional<LeasePayment> lp = leasePaymentRepository.findById(paymentReference);
		if(!lp.isPresent()) {
			throw new RuntimeException("Payment not found");
		}
		
		Optional<Sale> sale = saleRepository.findById(lp.get().getLeaseid());
		if(!sale.isPresent()){
			throw new RuntimeException("Sale not found");
		}
		
		Optional<User> customer = userRepository.findById(sale.get().getCustomerid());
		
		Notification notificaton = Notification.builder().type(NotificationType.SMS).address(lp.get().getPayeemobilenumber())
				.content(String.format(smsResendMessage, customer.get().getFirstname()+" "+customer.get().getLastname(),
						 sale.get().getScannedserial(),
						 lp.get().getAmount(), 
						 new SimpleDateFormat("yyyy-MM-dd").format(token.get().getToken()),						 
						 lp.get().getCreatedon())).build();
		
		rabbitTemplate.convertAndSend(notificationExchange,smsRoutingKey,notificaton.toString());
		
		return token.get();
	}

	@Transactional
	@Override
	public Token buyToken(BuyToken token) {
		return buyTokenRepo.commitPayment(token);
	}

	@Override
	public VCustomerFinanceInfo getBalanceForTokenPayment(String deviceserial) {
		
		Optional<VCustomerFinanceInfo> info = customerFinanceDao.findById(deviceserial);
		if(!info.isPresent()) {
			throw new RuntimeException("No Token Payments made for this device");
		}
		
		return info.get(); 
	}

	@Override
	public List<Token> getDeviceTokensBySerialNumber(String serialNumber) {
		// TODO Auto-generated method stub
		List<Token> result = new ArrayList<>();
		Optional<Sale> sale = saleRepository.findByScannedserial(serialNumber);
		if(!sale.isPresent()) {
			throw new RuntimeException("Sale with serial Number: "+serialNumber+" doesn't exist");
		}
		
		List<LeasePayment> leasePayments = leasePaymentRepository.findAllByleaseid(sale.get().getId());
		
		for(LeasePayment object : leasePayments) {
			Optional<Token> token = tokenRepository.findById(object.getId());
			if(token.isPresent()) {
				result.add(token.get());
			}
		}
		
		return result;
	}

	@Override
	public String resetDeviceToken(String serialNumber) {
		// TODO Auto-generated method stub
		
		Optional<PayGoProduct> payGoProduct = payGoProductRepo.findBytokenSerialNumber(serialNumber);
		if(!payGoProduct.isPresent()) {
			throw new RuntimeException("Device with serialNumber: "+serialNumber+" not found");
		}
		
		if(payGoProduct.get().getPayGoProductStatus().equals(PayGoProductStatus.AVAILABLE)) {
			throw new RuntimeException("Device not sold");
		}
		
		tokenOperation = new TokenOperation();
		return tokenOperation.generateGeneralPurposeToken(serialNumber, CommandNames.RESET, 1);
	}

}
