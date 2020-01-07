package com.fredastone.pandacore.repository;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.fredastone.pandacore.entity.Capex;
import com.fredastone.pandacore.entity.EmployeeMeta;

public interface CapexRepository extends CrudRepository<Capex, String>, PagingAndSortingRepository<Capex, String> {
	
	@Query("Select c from Capex c where TEmployees = :TEmployees")
	Page<Capex> findAllByEmployeeId(@Param("TEmployees") EmployeeMeta employee, Pageable pageable);
	
	Page<Capex> findAllByCreatedonBetween(Date from, Date to, Pageable pageable);

}