package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.fredastone.pandacore.entity.LeasePayment;

@Repository
public interface LeasePaymentRepository extends CrudRepository<LeasePayment, String> {
	
	
}
