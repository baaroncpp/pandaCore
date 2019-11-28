package com.fredastone.pandacore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fredastone.pandacore.entity.Role;

public interface RoleService {
	
	Role getRoleById(Short id);	
	Role addRole(Role role);
	Page<Role> getRoles(Pageable pageable);

}
