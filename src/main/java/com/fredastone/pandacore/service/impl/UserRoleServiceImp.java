package com.fredastone.pandacore.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.entity.Role;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.repository.RoleRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.repository.UserRoleRepository;
import com.fredastone.pandacore.service.UserRoleService;
import com.fredastone.pandacore.util.ServiceUtils;

@Service
public class UserRoleServiceImp implements UserRoleService {
	
	private UserRoleRepository userRoleRepository;
	private UserRepository userDao;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserRoleServiceImp(UserRoleRepository userRoleRepository, UserRepository userDao, RoleRepository roleRepository) {
		this.userRoleRepository = userRoleRepository;
		this.userDao = userDao;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserRole addUserRole(String userId, Short roleId) {
		//ROLE_CUSTOMER,ROLE_ENGINEER,ROLE_MARKETING,ROLE_FINANCE,ROLE_AGENT,ROLE_HR,ROLE_MANAGER,ROLE_SENIOR_MANAGER,ROLE_SUPPORT
		Optional<User> user = userDao.findById(userId);
		Optional<Role> r = roleRepository.findById(roleId); 
		
		Role role;
		String userType;
		
		if(!user.isPresent()) {
			throw new RuntimeException("User not found");
		}else if(!r.isPresent()){
			throw new RuntimeException("Role not found");
		}else {
			userType = user.get().getUsertype();
			role = r.get();
		}
		
		if(!userType.equals("CUSTOMER")) {
			userRoleRepository.save(createUserRole(role, user.get()));
			return createUserRole(role, user.get());			
		}else if(userType.equals("CUSTOMER") && (role.getName()).equals(RoleName.ROLE_CUSTOMER)){
			userRoleRepository.save(createUserRole(role, user.get()));
			return createUserRole(role, user.get());
		}else {
			throw new RuntimeException("User does not qualify for ROLE: " +role);
		}
		
		//notify for approval
		
	}
	
	public UserRole createUserRole(Role role, User user) {
		Optional<UserRole> userRole = userRoleRepository.findByUser(user);
		
		//if(userRole.isPresent()) {
		//	throw new RuntimeException("User already has role : "+ role.getName().name());
		//}
		UserRole userRole2 = new UserRole();
		userRole2.setUser(user);
		userRole2.setIsActive(Boolean.FALSE);
		userRole2.setRole(role);
		userRole2.setCreatedon(new Date());
		userRole2.setId(ServiceUtils.getUUID());
		
		return userRole2;
	}

	@Override
	public List<UserRole> getUserRoles(String userId) {
		
		Optional<User> user = userDao.findById(userId);
		
		if(!user.isPresent()) {
			throw new RuntimeException("User not found");
		}
		
		return userRoleRepository.findAllByUser(user.get());
	}

	

}
