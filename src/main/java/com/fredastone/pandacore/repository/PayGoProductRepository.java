package com.fredastone.pandacore.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.entity.PayGoProduct;

@Repository
public interface PayGoProductRepository extends CrudRepository<PayGoProduct, String>{
	
	//@Query(value="Select p from PayGoProduct u where u.productstatus = :payGoProductStatus and u.leaseofferid = :leaseofferid")
	//BigInteger getNumberOfAvailableProducts(@Param("payGoProductStatus") PayGoProductStatus payGoProductStatus, @Param("leaseofferid")int leaseofferid);

}
