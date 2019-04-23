package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Village;

@Repository
public interface VillageRepository  extends CrudRepository<Village, Integer>{

}
