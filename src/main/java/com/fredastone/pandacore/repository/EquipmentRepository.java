package com.fredastone.pandacore.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Equipment;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, String>,PagingAndSortingRepository<Equipment, String>{

	@Query("select u from Equipment u where u.serial = :serial")
	Equipment findEquipmentBySerial(@Param("serial") String serial);
}
