package com.fredastone.pandacore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.TotalLeasePayments;

@Repository
public interface TotalLeasePaymentsRepository extends CrudRepository<TotalLeasePayments, String>{
	
	Optional<TotalLeasePayments> findByleaseid(String id);

}
