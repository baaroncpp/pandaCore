package com.fredastone.pandacore.reportMgt.useCase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.Lease;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.PayGoProduct;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.entity.TotalLeasePayments;
import com.fredastone.pandacore.models.KeyValueModel;
import com.fredastone.pandacore.models.PaymentStatisticModel;
import com.fredastone.pandacore.models.SaleStatisticsModel;
import com.fredastone.pandacore.models.TokenRevenue;
import com.fredastone.pandacore.repository.AgentMetaRepository;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
import com.fredastone.pandacore.repository.LeaseOfferRepository;
import com.fredastone.pandacore.repository.LeasePaymentRepository;
import com.fredastone.pandacore.repository.LeaseRepository;
import com.fredastone.pandacore.repository.PayGoProductRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.TokenRepository;
import com.fredastone.pandacore.repository.TotalLeasePaymentsRepository;

@Service
@Transactional
public class SaleReport implements SaleReportInterface{
	
	private static final String TOKEN_REVENUE = "token_revenue";
	private static final String DEPOSIT_REVENUE = "deposit_revenue";
	
	private CustomerMetaRepository customerRepo;
	private SaleRepository saleRepo;
	private LeasePaymentRepository paymentRepo;
	private TotalLeasePaymentsRepository totalLeasePaymentRepository;
	private LeaseRepository leaseRepository;
	private TokenRepository tokenRepository;
	private PayGoProductRepository payGoRepository;
	private LeaseOfferRepository leaseOfferRepository;
	private AgentMetaRepository agentRepo;
	
	@Autowired
	public SaleReport(AgentMetaRepository agentRepo, LeaseOfferRepository leaseOfferRepository, PayGoProductRepository payGoRepository, TokenRepository tokenRepository,LeaseRepository leaseRepository, CustomerMetaRepository customerRepo, SaleRepository saleRepo, LeasePaymentRepository paymentRepo, TotalLeasePaymentsRepository totalLeasePaymentRepository) {
		this.customerRepo = customerRepo;
		this.saleRepo = saleRepo;
		this.paymentRepo = paymentRepo;
		this.totalLeasePaymentRepository = totalLeasePaymentRepository;
		this.leaseRepository = leaseRepository;
		this.tokenRepository = tokenRepository;
		this.payGoRepository = payGoRepository;
		this.leaseOfferRepository = leaseOfferRepository;
		this.agentRepo = agentRepo;
	}
	
	public SaleStatisticsModel getSaleStatistics(String leaseid) {
		
		Optional<Sale> sale = saleRepo.findById(leaseid);
		if(!sale.isPresent()) {
			throw new RuntimeException("Sale not found");
		}
		
		Optional<Lease> lease = leaseRepository.findById(leaseid);
		
		Optional<CustomerMeta> customer = customerRepo.findById(sale.get().getCustomerid());
		if(!customer.isPresent()) {
			throw new RuntimeException("Customer not found");
		}
		
		Optional<TotalLeasePayments> tlp = totalLeasePaymentRepository.findByleaseid(leaseid);
		if(!tlp.isPresent()) {
			throw new RuntimeException("Sale not approved");
		}
		
		List<LeasePayment> leasePayments = paymentRepo.findAllByleaseid(leaseid);
		
		SaleStatisticsModel ssm = new SaleStatisticsModel();
		ssm.setCustomer(customer.get());
		ssm.setPayments(leasePayments);
		ssm.setSale(sale.get());
		ssm.setTotalLeasePayments(tlp.get());
		ssm.setLease(lease.get());
		
		return ssm;
	}

	@Override
	public PaymentStatisticModel getPaymentStatisticModel(String userId) {
		// TODO Auto-generated method stub
		System.out.println(paymentRepo.countByCreatedonBeforeAndCreatedonAfter(getEndOfDay(new Date()), getStartOfDay(new Date())));
		//System.out.println(getDateMinusNDays(2, new Date()));
		//System.out.println(getDateMinusNMonths(2, new Date()));
		List<KeyValueModel> weekly = new ArrayList<>();
		List<KeyValueModel> monthly = new ArrayList<>();
		
		//add daily payments
		for(int i = 0; i < 7; i++) {
			System.out.println(getDateMinusNDays(i, new Date()));
			//System.out.println(getDateMinusNMonths(i, new Date()));
			
			Date date = getDateMinusNDays(i, new Date());			 
	        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
	        String abbrDate = simpleDateformat.format(date);
	        
	        System.out.println(abbrDate);
	        
			weekly.add(new KeyValueModel(abbrDate, paymentRepo.countByCreatedonBeforeAndCreatedonAfter(getEndOfDay(date), getStartOfDay(date))));
			//System.out.println( paymentRepo.countByCreatedonBeforeAndCreatedonAfter(getEndOfDay(date), getStartOfDay(date)));
		}
		
		//add for current month from month start to current date
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.DAY_OF_MONTH, 1);
		String monthName = new SimpleDateFormat("MMM").format(new Date());
		System.out.println(monthName);
		
		monthly.add(new KeyValueModel(monthName, paymentRepo.countByCreatedonBeforeAndCreatedonAfter(getEndOfDay(new Date()), getStartOfDay(cal2.getTime()))));
		//add monthly payments
		for(int i = 1; i < 12; i++) {
			
			Date mDate = getDateMinusNMonths(i, new Date());
			String monthName1 = new SimpleDateFormat("MMM").format(mDate);
			System.out.println(monthName1);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(mDate);
			
			//get last day of month
		    int res = cal.getActualMaximum(Calendar.DATE);
		    cal.set(Calendar.DAY_OF_MONTH, res);
		    Date lastMonthDay = cal.getTime();
		    
		    //get first day of the month
		    cal.set(Calendar.DAY_OF_MONTH, 1);
		    Date firstMonthDay = cal.getTime();
		    
		    monthly.add(new KeyValueModel(monthName1, paymentRepo.countByCreatedonBeforeAndCreatedonAfter(getEndOfDay(lastMonthDay), getStartOfDay(firstMonthDay))));
			
		}		
		
		PaymentStatisticModel paymentStatisticModel = new PaymentStatisticModel();
		paymentStatisticModel.setDailyPayments(paymentRepo.countByCreatedonBeforeAndCreatedonAfter(getEndOfDay(new Date()), getStartOfDay(new Date())));
		paymentStatisticModel.setWeeklyPayments(weekly);
		paymentStatisticModel.setMonthlyPayments(monthly);
		
		return paymentStatisticModel;
	}
	
	//generating days based on current date
	public static Date getDateMinusNDays(int days, Date date) {
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DATE, -days);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	//generating days based on current month
	public static Date getDateMinusNMonths(int months, Date date) {
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MONTH, -months);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getStartOfDay(Date date) {
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTime(date);
	     calendar.set(Calendar.HOUR_OF_DAY, 0);
	     calendar.set(Calendar.MINUTE, 0);
	     calendar.set(Calendar.SECOND, 0);
	     calendar.set(Calendar.MILLISECOND, 0);
	     return calendar.getTime();
	}
	
	public static Date getEndOfDay(Date date) {
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTime(date);
	     calendar.set(Calendar.HOUR_OF_DAY, 23);
	     calendar.set(Calendar.MINUTE, 59);
	     calendar.set(Calendar.SECOND, 59);
	     calendar.set(Calendar.MILLISECOND, 999);
	     return calendar.getTime();
	}
	 
	//generate month token revenue	
	@Override
	public List<TokenRevenue> tokenRevenues(Date date, String revenueType){
		
		List<TokenRevenue> dataResult = new ArrayList<>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		//get last day of month
	    int res = calendar.getActualMaximum(Calendar.DATE);
	    calendar.set(Calendar.DAY_OF_MONTH, res);
	    Date lastMonthDay = calendar.getTime();
	    
	    //get first day of the month
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    Date firstMonthDay = calendar.getTime();
	    
	    List<LeasePayment> lps = paymentRepo.findAllByCreatedonBeforeAndCreatedonAfter(getEndOfDay(lastMonthDay), getStartOfDay(firstMonthDay));
		
	    if(!lps.isEmpty()) {
	    	for(LeasePayment obj : lps) {
	    		
	    		Optional<Token> token = tokenRepository.findByleasepaymentid(obj.getId());
	    		if(!token.isPresent()) {
	    			throw new RuntimeException("Token not found");
	    		}
	    		
	    		Optional<Sale> sale = saleRepo.findById(obj.getLeaseid());
	    		if(!sale.isPresent()) {
	    			throw new RuntimeException("Sale not found");
	    		}
	    		
	    		Optional<Lease> lease = leaseRepository.findById(obj.getLeaseid());
	    		if(!lease.isPresent()) {
	    			throw new RuntimeException("lease not found");
	    		}
	    		
	    		TokenRevenue revenue = new TokenRevenue();
	    		revenue.setDate(obj.getCreatedon());
	    		revenue.setDeviceSerial(sale.get().getScannedserial());
	    		revenue.setToken(token.get().getToken());
	    		revenue.setAmount(obj.getAmount());
	    		
	    		if(revenueType.equals(DEPOSIT_REVENUE) && obj.getAmount() >= lease.get().getInitialdeposit()) {
	    			dataResult.add(revenue);
	    		}else if(revenueType.equals(TOKEN_REVENUE) && obj.getAmount() < lease.get().getInitialdeposit()) {
	    			dataResult.add(revenue);
	    		}
	    		
		    	
		    }
	    }	    
	    
		return dataResult;
	}

	@Transactional
	@Override
	public List<KeyValueModel> salesFinanceMetrics(Date date) {
		
		List<KeyValueModel> dataResult = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//get last day of month
	    int res = calendar.getActualMaximum(Calendar.DATE);
	    calendar.set(Calendar.DAY_OF_MONTH, res);
	    Date lastMonthDay = calendar.getTime();
	    
	    //get first day of the month
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    Date firstMonthDay = calendar.getTime();
		
		//value of inventory and stock at hands
	    //List<PayGoProduct> availableProducts = payGoRepository.findAllByPayGoProductStatus(PayGoProductStatus.AVAILABLE);
		Iterable<LeaseOffer> leaseOffers = leaseOfferRepository.findAll();
		
		for(LeaseOffer obj : leaseOffers) {
			System.out.println(obj.getName());
			List<PayGoProduct> availableProducts = payGoRepository.findAllByleaseOffer(obj);//findAllByPayGoProductStatus(PayGoProductStatus.AVAILABLE);
			long stockCount = 0;
			for(PayGoProduct object : availableProducts) {
				if(object.getPayGoProductStatus() == PayGoProductStatus.AVAILABLE) {
					stockCount = stockCount + 1;
				}
			}			
			//long stockCount = payGoRepository.countByPayGoProductStatusAndLeaseOffer1(PayGoProductStatus.AVAILABLE, obj.getId());
			long valueOfInventory = (long) (stockCount * obj.getProduct().getUnitcostselling());
			
			dataResult.add(new KeyValueModel("Stock At Hand("+obj.getProduct().getName()+")", stockCount));
			dataResult.add(new KeyValueModel("Value Of Inventory("+obj.getProduct().getName()+")", valueOfInventory));
		}
		
		//total number of monthly sales
		long monthSales = saleRepo.countByCreatedonBeforeAndCreatedonAfter(getEndOfDay(lastMonthDay), getStartOfDay(firstMonthDay));
		dataResult.add(new KeyValueModel("Total Sales ", monthSales));
		
		//total number monthly created customers
		long monthCustomers = customerRepo.countByCreatedonBeforeAndCreatedonAfter(getEndOfDay(lastMonthDay), getStartOfDay(firstMonthDay));
		dataResult.add(new KeyValueModel("Total New Accounts/Customers ", monthCustomers));	
		
		//total new monthly agents
		long monthAgents = agentRepo.countByCreatedonBeforeAndCreatedonAfter(getEndOfDay(lastMonthDay), getStartOfDay(firstMonthDay));
		dataResult.add(new KeyValueModel("Total New Agents ", monthAgents));
		
		return dataResult;
	}


}
