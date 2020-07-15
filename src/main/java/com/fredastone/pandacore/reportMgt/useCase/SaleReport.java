package com.fredastone.pandacore.reportMgt.useCase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.Lease;
import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.TotalLeasePayments;
import com.fredastone.pandacore.models.KeyValueModel;
import com.fredastone.pandacore.models.PaymentStatisticModel;
import com.fredastone.pandacore.models.SaleStatisticsModel;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
import com.fredastone.pandacore.repository.LeasePaymentRepository;
import com.fredastone.pandacore.repository.LeaseRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.TotalLeasePaymentsRepository;

@Service
@Transactional
public class SaleReport implements SaleReportInterface{
	
	private CustomerMetaRepository customerRepo;
	private SaleRepository saleRepo;
	private LeasePaymentRepository paymentRepo;
	private TotalLeasePaymentsRepository totalLeasePaymentRepository;
	private LeaseRepository leaseRepository;
	
	@Autowired
	public SaleReport(LeaseRepository leaseRepository, CustomerMetaRepository customerRepo, SaleRepository saleRepo, LeasePaymentRepository paymentRepo, TotalLeasePaymentsRepository totalLeasePaymentRepository) {
		this.customerRepo = customerRepo;
		this.saleRepo = saleRepo;
		this.paymentRepo = paymentRepo;
		this.totalLeasePaymentRepository = totalLeasePaymentRepository;
		this.leaseRepository = leaseRepository;
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
	
	//generating days based on current date
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

}
