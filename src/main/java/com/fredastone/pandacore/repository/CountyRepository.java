package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.County;

@Repository
public interface CountyRepository extends CrudRepository<County, Integer> {

}
