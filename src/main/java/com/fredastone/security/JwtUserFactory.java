package com.fredastone.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fredastone.pandacore.entity.TRole;
import com.fredastone.pandacore.entity.User;



public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create( User user) {
        return new JwtUser(
        		null, ""
           
        );
    }

    @SuppressWarnings("unused")
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<TRole> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
