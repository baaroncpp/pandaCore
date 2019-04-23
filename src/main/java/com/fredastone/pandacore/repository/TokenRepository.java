package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {

}
