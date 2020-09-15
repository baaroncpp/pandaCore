package com.fredastone.pandacore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.PayGoProduct;

@Repository
public interface PayGoProductRepository extends CrudRepository<PayGoProduct, String>{
	
	//@Query("Select count(u) from PayGoProduct u where u.payGoProductStatus = :payGoProductStatus and u.leaseOffer = :leaseoffer")
	//long countByPayGoProductStatusAndLeaseOffer1(@Param("payGoProductStatus")PayGoProductStatus payGoProductStatus, @Param("leaseoffer")String leaseoffer);
	
	List<PayGoProduct> findAllByleaseOffer(LeaseOffer leaseOffer);
	
	List<PayGoProduct> findAllByPayGoProductStatus(PayGoProductStatus payGoProductStatus);
	
	Optional<PayGoProduct> findBytokenSerialNumber(String tokenSerialNumber); 
	
	//long countByPayGoProductStatusAndLeaseOffer(PayGoProductStatus status, LeaseOffer leaseOffer);
	
}
