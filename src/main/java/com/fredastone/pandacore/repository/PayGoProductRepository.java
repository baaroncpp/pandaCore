package com.fredastone.pandacore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.PayGoProduct;

@Repository
public interface PayGoProductRepository extends CrudRepository<PayGoProduct, String>{
	
	@Query("Select count(u) from PayGoProduct u where u.payGoProductStatus = :payGoProductStatus and u.leaseOffer = :leaseoffer")
	Long countByPayGoProductStatusAndLeaseOffer(@Param("payGoProductStatus")PayGoProductStatus payGoProductStatus, @Param("leaseoffer")LeaseOffer leaseoffer);
	
	List<PayGoProduct> findAllByLeaseOffer(LeaseOffer leaseOffer);
}
