package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.District;


@Repository
public interface DistrictRepository extends CrudRepository<District,Integer>{

}
