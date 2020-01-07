package com.fredastone.pandacore.repository;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.entity.Opex;

public interface OpexRepository extends CrudRepository<Opex, String>, PagingAndSortingRepository<Opex, String> {
	
	@Query("Select o from Opex o where TEmployees = :TEmployees")
	Page<Opex> findAllByEmployee(@Param("TEmployees")EmployeeMeta meta, Pageable pageable);
	
	Page<Opex> findAllByCreatedonBetween(Date from, Date to, Pageable pageable);

}
