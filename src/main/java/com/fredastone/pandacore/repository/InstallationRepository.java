package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Installation;

@Repository
public interface InstallationRepository extends CrudRepository<Installation, String>,PagingAndSortingRepository<Installation, String>{

}
