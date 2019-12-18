package com.fredastone.pandacore.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fredastone.pandacore.entity.Role;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.RoleRepository;
import com.fredastone.pandacore.service.RoleService;

@Service
public class RoleServiceImp implements RoleService {
	
	RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImp(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role getRoleById(Short id) {
		Optional<Role> role = roleRepository.findById(id);
		
		if(!role.isPresent()) {
			throw new ItemNotFoundException(id.toString());
		}
		return role.get();
	}

	@Override
	public Role addRole(Role role) {
		Optional<Role> r = roleRepository.findByName(role.getName());
		
		if(r.isPresent()) {
			throw new RuntimeException(role.getName()+" already exists as a ROLE");
		}
		
		return roleRepository.save(role);
	}

	@Override
	public Page<Role> getRoles(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

}
