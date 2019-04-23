package com.fredastone.pandacore.service;

import com.fredastone.pandacore.entity.TotalLeasePayments;

public interface TotalLeasePaymentService {
	
	TotalLeasePayments getTotalLeasePayment(String customerId);
	TotalLeasePayments updateTotalLeasePayment(TotalLeasePayments tlp);
	TotalLeasePayments addTotalLeasePayments(TotalLeasePayments tlp);

}
