package com.fredastone.pandacore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.SaleRollback;

@Repository
public interface SaleRollbackRepository extends CrudRepository<SaleRollback, String>,PagingAndSortingRepository<SaleRollback, String> {

	Optional<SaleRollback> findBysale(Sale sale);
	
}
