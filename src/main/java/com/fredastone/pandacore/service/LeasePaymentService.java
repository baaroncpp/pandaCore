package com.fredastone.pandacore.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.fredastone.pandacore.entity.LeasePayment;

public interface LeasePaymentService {
	
	LeasePayment makeLeaseRepayment(LeasePayment payment);
	LeasePayment updateLeasePayment(LeasePayment payment);
	List<LeasePayment> getpaymentByCustomerId(int size,int page,String sortBy,String customerId);
	LeasePayment getPaymentById(String id);
	Page<LeasePayment> getAllPayments(int size,int page,String sortOrder);
	List<LeasePayment> getLeasePaymentsByLeaseId(String leaseid);
	List<LeasePayment> getLeasePaymentsByAgentId(String agentId, int size, int page, String sortOrder);
	//List<LeasePayment> getLeasePaymentByDeviceSerial(String serial);

}
