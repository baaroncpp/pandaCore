package com.fredastone.pandacore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fredastone.pandacore.entity.Role;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, String>{

	Optional<UserRole> findByUser(User user);

	List<UserRole> findAllByUser(User user);

	Optional<UserRole> findById(String userRoleId);
	
	Optional<UserRole> findByrole(Role role);
	
	@Query("Select u from UserRole u where u.role = :role and u.user = :user")
	Optional<UserRole> findByUserAndRole(@Param("user")User user, @Param("role")Role role);

}
