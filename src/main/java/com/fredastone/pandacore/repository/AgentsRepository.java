package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Agent;


@Repository
public interface AgentsRepository extends CrudRepository<Agent, Integer> {
	
}
