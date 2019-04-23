package com.fredastone.pandacore.service;

import java.util.List;

import com.fredastone.pandacore.entity.LeasePayment;

public interface LeasePaymentService {
	
	LeasePayment makeLeaseRepayment(LeasePayment payment);
	LeasePayment updateLeasePayment(LeasePayment payment);
	List<LeasePayment> getpaymentByCustomerId(int size,int page,String sortBy,String customerId);
	LeasePayment getPaymentById(String id);
	List<LeasePayment> getAllPayments(int size,int page,String sortOrder);

}
