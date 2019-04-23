package com.fredastone.pandacore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Config;


@Repository
public interface ConfigRepository extends CrudRepository<Config, Integer>{
	
	public Optional<Config> findByName(String name);
	
}
