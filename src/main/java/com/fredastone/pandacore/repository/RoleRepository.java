package com.fredastone.pandacore.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.entity.Role;


public interface RoleRepository extends CrudRepository<Role, Short>, PagingAndSortingRepository<Role, Short>{
	Page<Role> findAll(Pageable pageable);
	Optional<Role> findByName(RoleName rolename);
}
