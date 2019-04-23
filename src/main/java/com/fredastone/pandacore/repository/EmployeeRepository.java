package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.EmployeeMeta;


@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeMeta, String>,PagingAndSortingRepository<EmployeeMeta, String>{

}
