package com.fredastone.pandacore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.entity.Lease;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.repository.LeaseOfferRepository;
import com.fredastone.pandacore.repository.LeasePaymentRepository;
import com.fredastone.pandacore.repository.LeaseRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.service.LeasePaymentService;
import com.fredastone.pandacore.service.SaleService;
import com.fredastone.pandacore.util.ServiceUtils;

@Service
public class LeasePaymentServiceImpl implements LeasePaymentService {

	private LeasePaymentRepository lpDao;
	private LeaseRepository leaseReposotory;
	private SaleRepository saleRepository;
	private SaleService saleService;
	private LeaseOfferRepository leaseOfferRepository;
	
	@Autowired
	public LeasePaymentServiceImpl(LeasePaymentRepository lpDao,
			LeaseOfferRepository leaseOfferRepository,
			LeaseRepository leaseReposotory,
			SaleRepository saleRepository,
			SaleService saleService) {
		
		this.lpDao = lpDao;
		this.leaseReposotory = leaseReposotory;
		this.saleRepository = saleRepository;
		this.saleService = saleService;
		this.leaseOfferRepository = leaseOfferRepository;
	}
	
	@Override
	@Transactional
	public LeasePayment makeLeaseRepayment(LeasePayment payment) {
		/*
		Optional<Lease> lease = leaseReposotory.findById(payment.getLeaseid());
		Optional<Sale> sale = saleRepository.findById(payment.getLeaseid());
		LeaseOffer leaseOffer = lease.get().getLeaseOffer();
		
		if(!lease.isPresent()) {
			throw new RuntimeException("Lease of ID; "+payment.getLeaseid()+" not found");
		}
		
		if(!sale.isPresent()) {
			throw new RuntimeException("Sale of ID; "+payment.getLeaseid()+" not found");
		}
		
		float unitCost = sale.get().getAmount();
		float initialDeposit = lease.get().getInitialdeposit();
		
		float previousBalance = unitCost - getLeasePaymentsSum(initialDeposit, payment.getLeaseid());
		
		float currentLeasePaymentsSum = payment.getAmount() + getLeasePaymentsSum(initialDeposit, payment.getLeaseid());
		
		float currentBalance = unitCost - (previousBalance + payment.getAmount());
		
		//if payment completes sale
		if(previousBalance == payment.getAmount() && sale.get().isIsreviewed()) {
			
			saleService.completeSale(payment.getLeaseid());
			payment.setId(ServiceUtils.getUUID());
			return lpDao.save(payment);
			
		}
		
		*/
		
		return null;
	}
	
	public float getLeasePaymentsSum(float initialDeposit, String Leaseid) {
		return initialDeposit;
	
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
