package com.fredastone.pandacore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fredastone.pandacore.entity.LeasePaymentExtra;

public interface LeasePaymentExtraRepository extends CrudRepository<LeasePaymentExtra, String>{

	Optional<LeasePaymentExtra> findByleaseid(String leaseid);
}
