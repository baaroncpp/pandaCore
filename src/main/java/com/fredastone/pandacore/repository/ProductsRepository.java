package com.fredastone.pandacore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Product;

@Repository
public interface ProductsRepository extends CrudRepository<Product, String>{

	Optional<Product> findByName(String name);
	
}
