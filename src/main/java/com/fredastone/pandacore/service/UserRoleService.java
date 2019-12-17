package com.fredastone.pandacore.service;

import java.util.List;
import com.fredastone.pandacore.entity.Role;
import com.fredastone.pandacore.entity.UserRole;

public interface UserRoleService {
	
	UserRole addUserRole(String userId, Short roleId);
	
	List<UserRole> getUserRoles(String userId);
	
	UserRole removeUserRole(String userroleId);
	
	public List<Role> getRolesForUser(String userId);

}
