package com.fredastone.pandacore.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, String>{

	Optional<UserRole> findByUser(User user);

	List<UserRole> findAllByUser(User user);

	Optional<UserRole> findById(String userRoleId);
	
	//List<UserRole> getUser

}
