package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Subcounty;

@Repository
public interface SubCountyRepository extends CrudRepository<Subcounty, Integer> {

}
