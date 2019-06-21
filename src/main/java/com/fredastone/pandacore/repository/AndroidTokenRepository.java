package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.AndroidTokens;

@Repository
public interface AndroidTokenRepository extends CrudRepository<AndroidTokens, String>{

}
