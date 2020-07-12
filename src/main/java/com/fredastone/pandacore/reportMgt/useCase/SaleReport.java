package com.fredastone.pandacore.reportMgt.useCase;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.Lease;
import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.TotalLeasePayments;
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

}
