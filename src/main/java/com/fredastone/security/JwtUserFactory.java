package com.fredastone.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.entity.LoginUser;
import com.fredastone.pandacore.entity.Role;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.service.UserRoleService;

public final class JwtUserFactory {
	
	@Autowired
	private static UserRoleService userRoleService;
	
    private JwtUserFactory() { }
    
    public static JwtUser create( LoginUser user) {
    	JwtUser u = new JwtUser();
    	u.setId(user.getId());
    	u.setAccountActive(user.isIsactive());
    	u.setAccountApproved(user.isIsapproved());
    	u.setUsername(user.getUsername());
    	u.setPassword(user.getPassword());
    	u.setAuthorities(mapToGrantedAuthorities(user.getRoles()));
    	//u.setAuthorities(mapToGrantedAuthorities(getRoles(user.getId())));
    	u.setLastPasswordResetDate(user.getPasswordreseton());
    	return u;
    
    }

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
	
	@Transactional
	public static List<Role> getRoles(String userId){
		
		List<UserRole> userRoles = userRoleService.getUserRoles(userId);
		List<Role> roles = new ArrayList<>();
		
		for(UserRole object : userRoles) {
			roles.add(object.getRole());
		}
		
		return roles;
	}
	
}
