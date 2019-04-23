package com.fredastone.pandacore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.repository.LeasePaymentRepository;
import com.fredastone.pandacore.service.LeasePaymentService;

@Service
public class LeasePaymentServiceImpl implements LeasePaymentService {

	private LeasePaymentRepository lpDao;
	
	@Autowired
	public LeasePaymentServiceImpl(LeasePaymentRepository lpDao) {
		// TODO Auto-generated constructor stub
		this.lpDao = lpDao;
	}
	
	@Override
	public LeasePayment makeLeaseRepayment(LeasePayment payment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LeasePayment updateLeasePayment(LeasePayment payment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LeasePayment> getpaymentByCustomerId(int size, int page, String sortBy, String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LeasePayment getPaymentById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LeasePayment> getAllPayments(int size, int page, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

}
