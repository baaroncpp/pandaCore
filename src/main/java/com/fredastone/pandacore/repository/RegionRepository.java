package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Region;

@Repository
public interface RegionRepository extends CrudRepository<Region, Integer>{
	

}
