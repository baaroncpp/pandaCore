package com.fredastone.pandacore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.LeasePaymentRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.repository.UserRoleRepository;
import com.fredastone.pandacore.service.LeasePaymentService;

@Service
public class LeasePaymentServiceImpl implements LeasePaymentService {

	private LeasePaymentRepository lpDao;
	private SaleRepository saleRepository;
	private UserRepository userRepository;
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	public LeasePaymentServiceImpl(UserRoleRepository userRoleRepository,LeasePaymentRepository lpDao, SaleRepository saleRepository,	UserRepository userRepository) {
		this.lpDao = lpDao;
		this.saleRepository = saleRepository;
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
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
		
		Optional<LeasePayment> leasePayment = lpDao.findById(payment.getId());
		
		if(!leasePayment.isPresent()) {
			throw new ItemNotFoundException(payment.getId());
		}
		
		return lpDao.save(payment);
	}
	
	@Override
	public List<LeasePayment> getpaymentByCustomerId(int size, int page, String sortBy, String customerId) {
		
		List<LeasePayment> result = new ArrayList<>();
		List<Sale> sales = new ArrayList<>();
		Pageable pageable;
		
		if(sortBy.equalsIgnoreCase("DESC")) {
			
			pageable = PageRequest.of(page, size, Sort.by("createdon").descending());
			sales = saleRepository.findAllByCustomerid(customerId, pageable);
			
		}else if(sortBy.equalsIgnoreCase("ASC")) {
			
			pageable = PageRequest.of(page, size, Sort.by("createdon").descending());
			sales = saleRepository.findAllByCustomerid(customerId, pageable);
			
		}else {
			pageable = PageRequest.of(page, size);
			sales = saleRepository.findAllByCustomerid(customerId, pageable);
		}
		
		if(sales.isEmpty()) {
			throw new RuntimeException("No Sales made to customer with ID: "+customerId);
		}
		
		System.out.println(sales.size());
		
		for(Sale object : sales) {
			result.addAll(lpDao.findAllByleaseid(object.getId()));
		}
		
		if(result.isEmpty()) {
			throw new RuntimeException("No Lease payments made by customer with ID: "+customerId);
		}
		
		return result;
	}

	@Override
	public LeasePayment getPaymentById(String id) {
		
		Optional<LeasePayment> leasePayment = lpDao.findById(id);
		
		if(!leasePayment.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		return leasePayment.get();
	}

	@Override
	public Page<LeasePayment> getAllPayments(int size, int page, String sortOrder) {
		// TODO Auto-generated method stub		
		if(sortOrder.equalsIgnoreCase("DESC")) {
			return lpDao.findAll(PageRequest.of(page, size, Sort.by("createdon").descending()));
		}else if(sortOrder.equalsIgnoreCase("ASC")) {
			return lpDao.findAll(PageRequest.of(page, size, Sort.by("createdon").ascending()));
		}else {
			return lpDao.findAll(PageRequest.of(page, size));
		}
		
	}

	@Override
	public List<LeasePayment> getLeasePaymentsByLeaseId(String leaseid) {
		
		List<LeasePayment> leasePayments = lpDao.findAllByleaseid(leaseid);
		
		if(leasePayments.isEmpty()) {
			throw new RuntimeException("No payments with Lease ID: "+leaseid);
		}
		return leasePayments;
	}

	@Transactional
	@Override
	public List<LeasePayment> getLeasePaymentsByAgentId(String agentId, int size, int page, String sortOrder) {
		
		Optional<User> user = userRepository.findById(agentId);
		List<LeasePayment> result = new ArrayList<>();
		
		if(!user.isPresent()) {
			throw new ItemNotFoundException(agentId);
		}	
		
		List<UserRole> userRoles = userRoleRepository.findAllByUser(user.get());
		
		String userType = user.get().getUsertype();
		
		if(userType.equals(UserType.EMPLOYEE.name())) {
			
			for(UserRole object : userRoles) {
				if(object.getRole().getName().equals(RoleName.ROLE_MANAGER) || object.getRole().getName().equals(RoleName.ROLE_SENIOR_MANAGER)) {
					result.addAll(getAllPayments(size, page, sortOrder).getContent());
				}
			}
			
		}else if(userType.equals(UserType.AGENT.name())){
			
			List<Sale> saleList = saleRepository.findAllByAgentid(agentId);
			
			for(Sale object : saleList) {
				
				List<LeasePayment> salePayments = lpDao.findAllByleaseid(object.getId());
				
				if(!salePayments.isEmpty()) {
					result.addAll(salePayments);
				}
				
			}
		}
		return result;
	}
}
