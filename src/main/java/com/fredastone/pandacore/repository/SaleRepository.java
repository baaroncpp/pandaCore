package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Sale;

@Repository
public interface SaleRepository extends CrudRepository<Sale, String>,PagingAndSortingRepository<Sale, String>{

}
