package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fredastone.pandacore.entity.SaleRollBackRefund;

public interface SaleRollbackRefundRepository extends CrudRepository<SaleRollBackRefund, String> ,PagingAndSortingRepository<SaleRollBackRefund, String>{

}
