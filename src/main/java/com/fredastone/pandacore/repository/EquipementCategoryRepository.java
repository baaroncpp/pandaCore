package com.fredastone.pandacore.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.fredastone.pandacore.entity.EquipCategory;

public interface EquipementCategoryRepository extends CrudRepository<EquipCategory, String>{

	Optional<EquipCategory> findByName(String name);
}
