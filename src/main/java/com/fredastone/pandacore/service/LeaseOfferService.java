package com.fredastone.pandacore.service;

import java.util.List;
import java.util.Optional;

import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.models.LeaseOfferModel;

public interface LeaseOfferService {

	LeaseOffer addNewLeaseOffer(LeaseOfferModel lo);
	Optional<LeaseOffer> getLeaseOffer(int id);
	List<LeaseOffer> getLeaseOfferByProductId(String productId);
	Iterable<LeaseOffer> getAllLeaseOffers();
	LeaseOffer updateLeaseOffer(LeaseOfferModel lo);	
	
}
