package com.fredastone.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fredastone.pandacore.entity.LoginUser;
import com.fredastone.pandacore.entity.Role;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create( LoginUser user) {
    	JwtUser u = new JwtUser();
    	u.setId(user.getId());
    	u.setAccountActive(user.isIsactive());
    	u.setAccountApproved(user.isIsapproved());
    	u.setUsername(user.getUsername());
    	u.setPassword(user.getPassword());
    	u.setAuthorities(mapToGrantedAuthorities(user.getRoles()));
    	u.setLastPasswordResetDate(user.getPasswordreseton());
    	return u;
    
    }

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
