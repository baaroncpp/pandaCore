package com.fredastone.pandacore.service.impl;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.PayGoProduct;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.models.PayGoProductModel;
import com.fredastone.pandacore.repository.LeaseOfferRepository;
import com.fredastone.pandacore.repository.PayGoProductRepository;
import com.fredastone.pandacore.service.PayGoProductService;

@Service
public class PayGoProductServiceImp implements PayGoProductService {
	
	private PayGoProductRepository payGoProductRepository;
	private LeaseOfferRepository leaseOfferRepository;
	
	@Autowired
	public PayGoProductServiceImp(PayGoProductRepository payGoProductRepository, LeaseOfferRepository leaseOfferRepository) {
		this.payGoProductRepository = payGoProductRepository;
		this.leaseOfferRepository = leaseOfferRepository;
	}

	@Override
	public PayGoProduct addPayGoProduct(PayGoProductModel payGoProductModel) {
		
		Optional<LeaseOffer> leaseOffer = leaseOfferRepository.findById(payGoProductModel.getLeaseOfferid());
		
		if(!leaseOffer.isPresent()) {
			throw new ItemNotFoundException(String.valueOf(payGoProductModel.getLeaseOfferid()));
		}
		
		PayGoProduct payGoProduct = new PayGoProduct();
		payGoProduct.setTokenSerialNumber(payGoProductModel.getTokenSerialNumber());
		payGoProduct.setLeaseOffer(leaseOffer.get());
		payGoProduct.setPayGoProductStatus(PayGoProductStatus.AVAILABLE);
		
		payGoProductRepository.save(payGoProduct);
		
		return payGoProduct;
	}

	@Override
	public PayGoProduct updatePayGoProduct(PayGoProduct payGoProduct) {
		
		Optional<PayGoProduct> payGoProd = payGoProductRepository.findById(payGoProduct.getTokenSerialNumber());
		
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
		Optional<PayGoProduct> payGoProduct = payGoProductRepository.findById(tokenSerialNumber);
		
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
		
		Optional<PayGoProduct> payGoProduct = payGoProductRepository.findById(serialToken);
		
		if(!payGoProduct.isPresent()) {
			throw new ItemNotFoundException(serialToken);
		}
		return payGoProduct.get();
	}

}
