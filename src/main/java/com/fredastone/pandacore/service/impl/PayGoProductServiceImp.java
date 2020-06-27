package com.fredastone.pandacore.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.PayGoProduct;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.models.PayGoProductModel;
import com.fredastone.pandacore.repository.LeaseOfferRepository;
import com.fredastone.pandacore.repository.PayGoProductRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.PayGoProductService;
import com.fredastone.pandacore.util.ServiceUtils;
import com.fredastone.pandacore.models.StockProduct;

@Service
public class PayGoProductServiceImp implements PayGoProductService {
	
	private PayGoProductRepository payGoProductRepository;
	private LeaseOfferRepository leaseOfferRepository;
	private UserRepository userRepository;
	
	@Autowired
	public PayGoProductServiceImp(UserRepository userRepository, PayGoProductRepository payGoProductRepository, LeaseOfferRepository leaseOfferRepository) {
		this.payGoProductRepository = payGoProductRepository;
		this.leaseOfferRepository = leaseOfferRepository;
		this.userRepository = userRepository;
	}

	@Override
	public PayGoProduct addPayGoProduct(String userId, PayGoProductModel payGoProductModel) {
		
		Optional<User> user = userRepository.findById(userId);
		
		if(!user.get().getUsertype().equals(UserType.EMPLOYEE.name())) {
			throw new RuntimeException("Not Authorized");
		}
		
		Optional<LeaseOffer> leaseOffer = leaseOfferRepository.findById(payGoProductModel.getLeaseOfferid());
		
		if(!leaseOffer.isPresent()) {
			throw new ItemNotFoundException(String.valueOf(payGoProductModel.getLeaseOfferid()));
		}
		
		Optional<PayGoProduct> payGo = payGoProductRepository.findBytokenSerialNumber(payGoProductModel.getTokenSerialNumber());		
		if(payGo.isPresent()){
			throw new RuntimeException("PayGo product with serial number "+payGoProductModel.getTokenSerialNumber()+" already exists");
		}
		
		PayGoProduct payGoProduct = new PayGoProduct();
		payGoProduct.setTokenSerialNumber(payGoProductModel.getTokenSerialNumber());
		payGoProduct.setLeaseOffer(leaseOffer.get());
		payGoProduct.setPayGoProductStatus(PayGoProductStatus.AVAILABLE);
		payGoProduct.setId(ServiceUtils.getUUID());
		
		payGoProductRepository.save(payGoProduct);
		
		return payGoProduct;
	}

	@Override
	public PayGoProduct updatePayGoProduct(PayGoProduct payGoProduct) {
		
		Optional<PayGoProduct> payGoProd = payGoProductRepository.findById(payGoProduct.getId());
		
		if(!payGoProd.isPresent()) {
			throw new RuntimeException("Product with serial number "+payGoProduct.getTokenSerialNumber()+" does not exist");
		}
		
		if(!(payGoProd.get().getPayGoProductStatus()).equals(PayGoProductStatus.AVAILABLE)) {
			throw new RuntimeException("Operation does not apply "+payGoProduct.getTokenSerialNumber());
		}
		
		payGoProductRepository.save(payGoProd.get());
		return payGoProd.get();
	}

	@Override
	public PayGoProduct deletePayGoProduct(PayGoProduct payGoProduct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getAvailablePayGoProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPayGoPayGoProductAvailable(String tokenSerialNumber) {
		Optional<PayGoProduct> payGoProduct = payGoProductRepository.findBytokenSerialNumber(tokenSerialNumber);
		
		if(!payGoProduct.isPresent()) {
			return false;
		}else if(!(payGoProduct.get().getPayGoProductStatus()).equals(PayGoProductStatus.AVAILABLE)){
			return false;
		}else {
			return true;
		}
	}

	@Override
	public PayGoProduct getPayGoProduct(String serialToken) {
		
		Optional<PayGoProduct> payGoProduct = payGoProductRepository.findBytokenSerialNumber(serialToken);
		
		if(!payGoProduct.isPresent()) {
			throw new ItemNotFoundException(serialToken);
		}
		return payGoProduct.get();
	}
	
	@Override
	@Transactional 
	public long countPayGoByStatus(PayGoProductStatus status, int leaseOfferid) {
		
		List<PayGoProduct> result = new ArrayList<>();
		Optional<LeaseOffer> leaseOffer = leaseOfferRepository.findById(leaseOfferid);
		LeaseOffer lo = leaseOffer.get();
		
		List<PayGoProduct> payGos = payGoProductRepository.findAllByLeaseOffer(lo);
		
		for(PayGoProduct object : payGos) {
			if(object.getPayGoProductStatus() == status) {
				result.add(object);
			}
		}
		return result.size();
	}
	
	@Override
	public List<StockProduct> getPayGoStockValues(){
		
		List<StockProduct> result = new ArrayList<>();
		
		Iterable<LeaseOffer> leaseOffers = leaseOfferRepository.findAll();
		
		for(LeaseOffer object : leaseOffers) {
			StockProduct stockProduct = new StockProduct();
			stockProduct.setLeaseOffer(object);
			stockProduct.setStockValue(countPayGoByStatus(PayGoProductStatus.AVAILABLE, object.getId()));
			//stockProduct.setStockValue((long) 10);
			result.add(stockProduct);
		}
		
		return result;
	}

}
