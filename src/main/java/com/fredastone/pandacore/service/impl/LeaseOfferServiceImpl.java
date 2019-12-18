package com.fredastone.pandacore.service.impl;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.Product;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.exception.ProductNotFoundException;
import com.fredastone.pandacore.models.LeaseOfferModel;
import com.fredastone.pandacore.repository.LeaseOfferRepository;
import com.fredastone.pandacore.repository.ProductsRepository;
import com.fredastone.pandacore.service.LeaseOfferService;

@Service
public class LeaseOfferServiceImpl implements LeaseOfferService{

	private LeaseOfferRepository leaseOfferDao;
	private ProductsRepository productsRepository;
	
	@Autowired
	public LeaseOfferServiceImpl(LeaseOfferRepository leaseOfferDao,ProductsRepository productsRepository) {
		// TODO Auto-generated constructor stub
		
		this.leaseOfferDao = leaseOfferDao;
		this.productsRepository = productsRepository;
	}

	@Override
	public LeaseOffer addNewLeaseOffer(LeaseOfferModel lo) {
		// TODO Auto-generated method stub
		
		Optional<Product> product = productsRepository.findById(lo.getProductid());
		
		if(!product.isPresent()) {
			throw new ProductNotFoundException(lo.getProductid());
		}
		
		LeaseOffer offer = new LeaseOffer();
		offer.setCode(lo.getCode());
		offer.setDescription(lo.getDescription());
		offer.setIntialdeposit((long) lo.getInitialdeposit());
		offer.setIsactive(Boolean.TRUE);
		offer.setLeaseperiod(lo.getLeaseperiod());
		offer.setName(lo.getName());
		offer.setPercentlease(lo.getPercentlease());
		offer.setProduct(product.get());
		offer.setRecurrentpaymentamount((long) lo.getRecurrentpaymentamount());
		
		return leaseOfferDao.save(offer);
	}

	@Override
	public Optional<LeaseOffer> getLeaseOffer(int id) {
		// TODO Auto-generated method stub
		Optional<LeaseOffer> leaseOffer = leaseOfferDao.findById(id);
		
		if(!leaseOffer.isPresent()) {
			throw new RuntimeException("LeaseOffer of ID:"+id+"does not exist");
		}
		
		return leaseOfferDao.findById(id);
	}

	@Override
	public List<LeaseOffer> getLeaseOfferByProductId(String productId) {
		
		List<LeaseOffer> leaseOffers = leaseOfferDao.findByProductId(productId);
		
		if(leaseOffers.isEmpty()) {
			throw new RuntimeException("No LeaseOffers associated with product of ID : "+productId);
		}
		
		return leaseOffers;
	}

	@Override
	public Iterable<LeaseOffer> getAllLeaseOffers() {
		return leaseOfferDao.findAll();
	}

	@Override
	@Transactional
	public LeaseOffer updateLeaseOffer(LeaseOfferModel lo) {
		// TODO Auto-generated method stub
		
		Optional<LeaseOffer> offer = leaseOfferDao.findById(lo.getId());
		
		if(!offer.isPresent())
		{
			throw new ItemNotFoundException(String.valueOf(lo.getId()));
		}
		
		LeaseOffer tmp = offer.get();
		tmp.setCode(lo.getCode());
		tmp.setDescription(lo.getDescription());
		tmp.setIntialdeposit((long) lo.getInitialdeposit());
		tmp.setIsactive(lo.isIsactive());
		tmp.setLeaseperiod(lo.getLeaseperiod());
		tmp.setName(lo.getName());
		tmp.setPercentlease(lo.getPercentlease());
		tmp.setRecurrentpaymentamount((long) lo.getRecurrentpaymentamount());

		return leaseOfferDao.save(tmp);
	}
	
	
}
