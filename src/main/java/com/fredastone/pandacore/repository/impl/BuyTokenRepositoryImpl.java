package com.fredastone.pandacore.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.fredastone.pandacore.constants.TokenTypes;
import com.fredastone.pandacore.entity.Lease;
import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.LeasePaymentExtra;
import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.entity.TotalLeasePayments;
import com.fredastone.pandacore.entity.VCustomerFinanceInfo;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.exception.LowTransactionValueException;
import com.fredastone.pandacore.exception.PaymentDetailsNotFoundException;
import com.fredastone.pandacore.models.BuyToken;
import com.fredastone.pandacore.models.Notification;
import com.fredastone.pandacore.models.Notification.NotificationType;
import com.fredastone.pandacore.repository.BuyTokenRepository;
import com.fredastone.pandacore.repository.CustomerFinanceInfoRepository;
import com.fredastone.pandacore.repository.LeasePaymentExtraRepository;
import com.fredastone.pandacore.repository.LeasePaymentRepository;
import com.fredastone.pandacore.repository.LeaseRepository;
import com.fredastone.pandacore.repository.TokenRepository;
import com.fredastone.pandacore.util.ServiceUtils;
import com.fredastone.pandasolar.token.CommandNames;
import com.fredastone.pandasolar.token.TokenOperation;

@Repository
public class BuyTokenRepositoryImpl implements BuyTokenRepository {

	@Value("${notification.exchange.name}")
	private String notificationExchange;
//
//	@Value("${notification.queue.email.name}")
//	private String emailQueue;

	@Value("${notification.queue.sms.name}")
	private String smsQueue;

	@Value("${notification.routing.sms.key}")
	private String smsRoutingKey;
	
	@Value("${notification.smsnotification}")
	private String smsMessage;
	
	@Value("${notification.sms.lowdailypayment.notification}")
	private String smsMessageLowDailyPayment;
	
	@Value("${notification.sms.lowinitialpayment.notification}")
	private String smsMessageLowInitialPayment;
	
	@Value("${notification.sms.loancleared.notification}")
	private String smsMessageLoanCleared;
	
//	@Value("${notification.routing.email.key}")
//	private String emailRoutingKey;
//	
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private CustomerFinanceInfoRepository financialInfoDao;
	
	@Autowired
	private LeasePaymentExtraRepository leasePaymentExtra;
	
	@Autowired
	private LeasePaymentRepository leasePaymentDao;
	
	@Autowired
    private TokenRepository tokenRespository;
	
	@Autowired
	private LeaseRepository leaseRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private TokenOperation tokenOperation;
	
	private static final short PAYMENT_COMPLETED_STATUS = 2;
	@SuppressWarnings("unused")
	private static final short PAYMENT_FAILED_STATUS = 3;
	@SuppressWarnings("unused")
	private static final short PAYMENT_PENDING_STATUS = 1;
	
	private static  final String TOTAL_PAYMENTS_SELECT_QUERY = "Select * from panda_core.t_total_lease_payments WHERE leaseid = :leaseid FOR UPDATE";
	
	private static final String TOTAL_PAYMENTS_UPDATE_QUERY = "UPDATE panda_core.t_total_lease_payments SET "
			+ "lastpaidamount = :lastpaidamount,totalamountpaid = :totalamountpaid,totalamountowed = :totalamountowed,residueamount = :residueamount,"
			+ "times = :times WHERE leaseid = :leaseid";
	
	private final static  String LEASE_SELECT_QUERY = "SELECT id, iscompleted,isactivated FROM panda_core.t_lease WHERE id = :leaseid AND isactivated = TRUE FOR UPDATE";
	
	private final static String LEASE_UPDATE_QUERY = "UPDATE panda_core.t_lease SET iscompleted = TRUE,paymentcompletedon = now() WHERE id = :leaseid";

	public BuyTokenRepositoryImpl() {
		
		tokenOperation = new TokenOperation();
		
	}
	
	@Transactional
	@Override
	public Token commitPayment(BuyToken paymentRequest) {
		
		Optional<VCustomerFinanceInfo> financialInfo = financialInfoDao.findById(paymentRequest.getDeviceserial());
		if(!financialInfo.isPresent())
			throw new PaymentDetailsNotFoundException("Failed to find financial info for device serial "+paymentRequest.getDeviceserial());
		
		
		Map<String,Object> args = new HashMap<>();
		args.put("leaseid", financialInfo.get().getLeaseid());
		
		TotalLeasePayments ttlpayments = jdbcTemplate.queryForObject(TOTAL_PAYMENTS_SELECT_QUERY, args, new TotalLeasePaymentsRowMapper());
		
		if(ttlpayments == null)
		{
			//No lease payment made before
			throw new PaymentDetailsNotFoundException("Failed to find entry for total payments, check that sale is approved for device serial "+paymentRequest.getDeviceserial());
		}
		
		//
		if(ttlpayments.getTotalamountpaid() == 0) {
			
			Optional<Lease> leaseSale = leaseRepository.findById(financialInfo.get().getLeaseid());
			if(!leaseSale.isPresent()) {
				throw new RuntimeException("Failed to find Lease sale, check that sale is approved for device serial "+paymentRequest.getDeviceserial());
			}
			
			if(leaseSale.get().getInitialdeposit() > paymentRequest.getAmount()) {
				final Notification notificaton = Notification.builder().type(NotificationType.SMS).address(paymentRequest.getMsisdn())
						.content(
								String.format(smsMessageLowInitialPayment, financialInfo.get().getFirstname()+" "+(financialInfo.get().getMiddlename() == null ? "" : financialInfo.get().getMiddlename()+" ") +
										financialInfo.get().getLastname(), leaseSale.get().getInitialdeposit())
								).build();
				
				rabbitTemplate.convertAndSend(notificationExchange,smsRoutingKey,notificaton.toString());
				
				throw new LowTransactionValueException("The amount is less than the initial loan deposit");
			}
			
			final float residueAmount = paymentRequest.getAmount()%leaseSale.get().getInitialdeposit();
			
			final float ammountPaid = ttlpayments.getTotalamountpaid() + paymentRequest.getAmount();
			
			//final int totalDays = (int)((totalAmount - residueAmount)/financialInfo.get().getDailypayment());
			
			final float newOwedAmount = leaseSale.get().getTotalleasevalue() - paymentRequest.getAmount();
			
			if(updateTotalPayments(financialInfo.get().getLeaseid(), paymentRequest.getAmount(), ammountPaid - residueAmount,
					newOwedAmount, residueAmount,ttlpayments.getTimes()+1) < 1) {
				throw new RuntimeException("Failed to update totalPayments");
			}
			
			final String paymentToken = getPaymentToken(financialInfo.get().getDeviceserial(), ttlpayments.getTimes()+1, 1);
			
			//Check if we have to get payment token or unlock the device
			final LeasePayment lp = getCompletedLeasePayment(paymentRequest,financialInfo.get());
			
			final int times =  ttlpayments.getTimes()+1;
			
			leasePaymentDao.save(lp);
			
			//TODO Place message on queue here
			
			final Notification notificaton = Notification.builder().type(NotificationType.SMS).address(paymentRequest.getMsisdn())
					.content(String.format(smsMessage, financialInfo.get().getFirstname()+" "+(financialInfo.get().getMiddlename() == null ? "" : financialInfo.get().getLastname()+" ") +
									financialInfo.get().getLastname(),
									paymentRequest.getAmount(),paymentRequest.getDeviceserial(),paymentToken, newOwedAmount)
							).build();
			
			rabbitTemplate.convertAndSend(notificationExchange,smsRoutingKey,notificaton.toString());
			
			int totalDays = leaseSale.get().getTotalleaseperiod();
			
			return recordToken(TokenTypes.PAY, paymentToken, 1, times,lp.getId() );
			//return null;
		}else{
			
			final float totalAmount = ttlpayments.getResidueamount() + paymentRequest.getAmount();
			
			//Check that amount meets daily minimum allowed amount
			if(totalAmount < financialInfo.get().getDailypayment())	{
				
				final Notification notificaton = Notification.builder().type(NotificationType.SMS).address(paymentRequest.getMsisdn())
						.content(
								String.format(smsMessageLowDailyPayment, financialInfo.get().getFirstname()+" "+(financialInfo.get().getMiddlename() == null ? "" : financialInfo.get().getMiddlename()+" ") +
										financialInfo.get().getLastname(), financialInfo.get().getDailypayment())
								).build();
				
				rabbitTemplate.convertAndSend(notificationExchange,smsRoutingKey,notificaton.toString());
				
				throw new LowTransactionValueException("The amount is less than the daily allowed minimum limit");
			}
			
			//Check if this is last payment
			//Last payment could be divided and 
			//If last payment, take total owed, put rest in pool for refund
			boolean isFinalPayment = Boolean.FALSE;
			if(ttlpayments.getTotalamountowed() <= totalAmount) {
				isFinalPayment = Boolean.TRUE;
			}
			
			if(isFinalPayment)
			{
				
				final LeasePayment lp = getCompletedLeasePayment(paymentRequest,financialInfo.get());
				
				leasePaymentDao.save(lp);
				
				
				final String clearPaymentToken = completeLeaseAndUnlockDevice
						(ttlpayments.getLastpaidamount(), 
							ttlpayments.getTotalamountpaid(), 
							totalAmount - ttlpayments.getTotalamountowed(),
							ttlpayments.getTimes(), 
							financialInfo.get().getLeaseid(), 
							financialInfo.get().getDeviceserial());
				
				final Notification notificaton = Notification.builder().type(NotificationType.SMS).address(paymentRequest.getMsisdn())
						.content(
								String.format(smsMessageLoanCleared, financialInfo.get().getFirstname()+" "+(financialInfo.get().getMiddlename() == null ? "" : financialInfo.get().getMiddlename()+" ") +
										financialInfo.get().getLastname(),
										paymentRequest.getAmount(), paymentRequest.getDeviceserial(), clearPaymentToken)
								).build();
				
				rabbitTemplate.convertAndSend(notificationExchange,smsRoutingKey,notificaton.toString());
				
				return recordToken(TokenTypes.OPEN, clearPaymentToken, 0, ttlpayments.getTimes(),lp.getId() );
				
				//Need to place token on message queue here
												
			}
			
			final float residueAmount = totalAmount%financialInfo.get().getDailypayment();
			
			final int totalDays = (int)((totalAmount - residueAmount)/financialInfo.get().getDailypayment());
			
			final float newOwedAmount = ttlpayments.getTotalamountowed() - totalAmount - residueAmount;
			
			//Check if we have to get payment token or unlock the device
			final LeasePayment lp = getCompletedLeasePayment(paymentRequest,financialInfo.get());
			
			final int times =  ttlpayments.getTimes()+1;
			
			leasePaymentDao.save(lp);
			
			if(updateTotalPayments(financialInfo.get().getLeaseid(), totalAmount, totalAmount - residueAmount,
					newOwedAmount, residueAmount,times) < 1) {
				throw new RuntimeException("Failed to update totalPayments");
			}
			
			//Place token on message queue for sending to customer
			
			final String paymentToken = getPaymentToken(financialInfo.get().getDeviceserial(), times, totalDays);
			

			//TODO Place message on queue here
			
			final Notification notificaton = Notification.builder().type(NotificationType.SMS).address(paymentRequest.getMsisdn())
					.content(
							String.format(smsMessage, financialInfo.get().getFirstname()+" "+(financialInfo.get().getMiddlename() == null ? "" : financialInfo.get().getMiddlename()+" ") +
									financialInfo.get().getLastname(),
									paymentRequest.getAmount(), paymentRequest.getDeviceserial(), paymentToken, newOwedAmount)
							).build();
			
			rabbitTemplate.convertAndSend(notificationExchange,smsRoutingKey,notificaton.toString());

			return recordToken(TokenTypes.PAY, paymentToken,totalDays, times,lp.getId() );
		
			
		}
		
		
	}

	
	private int updateTotalPayments(String leaseid,float lastpaidamount,float amountpaid,float amountowed,float residueamount,int times) {
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("leaseid", leaseid);
		map.put("lastpaidamount", lastpaidamount);
		map.put("totalamountpaid", amountpaid);
		map.put("totalamountowed", amountowed);
		map.put("residueamount",residueamount);
		map.put("times", times);
		
		return jdbcTemplate.update(TOTAL_PAYMENTS_UPDATE_QUERY, map);
	}
	
	private String getPaymentToken(String deviceSerial,int times,int days) {
		
		return tokenOperation.generatePaymentToken(deviceSerial, times, String.valueOf(days));
				
	}
	
	private Token recordToken(TokenTypes tokenType,String token, int days, int times, String paymentid) {
		
		Token t = new Token();
		t.setToken(token);
		t.setDays(days);
		t.setLeasepaymentid(paymentid);
		t.setTimes(times);
		t.setType(tokenType);
		
		
		return tokenRespository.save(t);
		
	}
	
	
	private LeasePayment getCompletedLeasePayment(BuyToken bt,VCustomerFinanceInfo ci) {
		
		LeasePayment payment = new LeasePayment();
		
		payment.setAmount(bt.getAmount());
		payment.setChannelmessage(bt.getChannelmessage());
		payment.setChannelstatuscode(bt.getChannelstatuscode());
		payment.setChanneltransactionid(bt.getChanneltransactionid());
		payment.setLeaseid(ci.getLeaseid());
		payment.setPayeemobilenumber(bt.getMsisdn());
		payment.setPaymentchannel(bt.getChannel());
		payment.setPaymentstatus(PAYMENT_COMPLETED_STATUS);
		payment.setId(ServiceUtils.getUUID());
		payment.setTransactionid(bt.getTransactionId());
		payment.setPayeename(bt.getPayeename());
		
		return payment;
		
	}
	
	private String completeLeaseAndUnlockDevice(float lastPaidAmount,float totalpaid,float extraPayment,int times,String leaseid,String deviceSerial) {
		
		if(updateTotalPayments(leaseid, lastPaidAmount, totalpaid, 0, 0, times) < 0)
			throw new RuntimeException("Failed to process payment, Final payment entry failed");
		
		
		//Get the lease and update it to done;
		Map<String,Object> args = new HashMap<>();
		args.put("leaseid", leaseid);
		Lease l = jdbcTemplate.queryForObject(LEASE_SELECT_QUERY, args, new LeaseMapper());
		
		if(l == null)
			throw new RuntimeException("Failed to process payment, Final payment entry failed");
		
		if(jdbcTemplate.update(LEASE_UPDATE_QUERY, args) < 0)
			throw new RuntimeException("Failed to process payment, Final payment entry failed");
		
		if(extraPayment > 0)
		{
			
			LeasePaymentExtra  lpe = new LeasePaymentExtra();
			
			lpe.setAmount(extraPayment);
			lpe.setIsrefunded(Boolean.FALSE);
			lpe.setId(ServiceUtils.getUUID());
			lpe.setLeaseid(leaseid);
			
			leasePaymentExtra.save(lpe);
			
		}
		
		//Generate token for completed Payment
		return tokenOperation.generateGeneralPurposeToken(deviceSerial, CommandNames.CLEAR_LOAN, 1);
		
	}
	
	class TotalLeasePaymentsRowMapper implements RowMapper<TotalLeasePayments>{

		@Override
		public TotalLeasePayments mapRow(ResultSet rs, int arg1) throws SQLException {
			
			TotalLeasePayments tlp = new TotalLeasePayments();
			
			tlp.setId(rs.getString("id"));
			tlp.setLastpaidamount(rs.getFloat("lastpaidamount"));
			tlp.setLeaseid(rs.getString("leaseid"));
			tlp.setNextpaymentdate(rs.getDate("nextpaymentdate"));
			tlp.setTimes(rs.getInt("times"));
			tlp.setResidueamount(rs.getFloat("residueamount"));
			tlp.setTotalamountowed(rs.getFloat("totalamountowed"));
			tlp.setTotalamountpaid(rs.getFloat("totalamountpaid"));
						
			return tlp;
			
		}
		
	}
	
	class LeaseMapper implements RowMapper<Lease>{

		@Override
		public Lease mapRow(ResultSet rs, int arg1) throws SQLException {
			Lease l = new Lease();
			l.setId(rs.getString("id"));
			l.setIscompleted(rs.getBoolean("iscompleted"));
			l.setIsactivated(rs.getBoolean("isactivated"));
			
			return l;
			
		}
		
	}

	public <S extends Token> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public <S extends Token> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}


	public Optional<Token> findById(String id) {
		
		Optional<Token> token = tokenRespository.findById(id);
		
		if(!token.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		return token;
	}


	public boolean existsById(String id) {
		Optional<Token> token = tokenRespository.findById(id);
				
		if(!token.isPresent()) {
			return Boolean.FALSE;
		}else {
			return Boolean.TRUE;
		}
	}


	public Iterable<Token> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	public Iterable<Token> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}


	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}


	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}


	public void delete(Token entity) {
		// TODO Auto-generated method stub
		
	}


	public void deleteAll(Iterable<? extends Token> entities) {
		// TODO Auto-generated method stub
		
	}

	
}
