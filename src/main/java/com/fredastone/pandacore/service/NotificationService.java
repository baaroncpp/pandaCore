package com.fredastone.pandacore.service;

import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.Sale;

public interface NotificationService {

	void approveSaleNotification(Sale sale);
	void paymentNotification(LeasePayment payment);
	void approvedSaleNotification(Sale sale);
	
}
