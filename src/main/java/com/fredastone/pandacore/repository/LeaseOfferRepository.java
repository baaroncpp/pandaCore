package com.fredastone.pandacore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.LeaseOffer;

@Repository
public interface LeaseOfferRepository extends CrudRepository<LeaseOffer, Integer>{

	@Query("Select p from LeaseOffer p where p.product.id = :productId")
	List<LeaseOffer> findByProductId(String productId);
}
