package com.fredastone.pandacore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.TotalLeasePayments;
import com.fredastone.pandacore.repository.TotalLeasePaymentsRepository;
import com.fredastone.pandacore.service.TotalLeasePaymentService;

@Service
public class TotalLeasePaymentServiceImpl implements TotalLeasePaymentService {

	
	private TotalLeasePaymentsRepository tlpsRepository;
	
	@Autowired
	public TotalLeasePaymentServiceImpl(TotalLeasePaymentsRepository tlpsRepository) {
		// TODO Auto-generated constructor stub
		this.tlpsRepository = tlpsRepository;
	}
	
	@Override
	public TotalLeasePayments getTotalLeasePayment(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TotalLeasePayments updateTotalLeasePayment(TotalLeasePayments tlp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TotalLeasePayments addTotalLeasePayments(TotalLeasePayments tlp) {
		// TODO Auto-generated method stub
		return null;
	}

}
