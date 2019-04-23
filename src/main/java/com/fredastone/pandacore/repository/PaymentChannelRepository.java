package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.PaymentChannel;


@Repository
public interface PaymentChannelRepository extends CrudRepository<PaymentChannel, Integer>{

}
