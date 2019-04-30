package com.fredastone.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.LoginUser;
import com.fredastone.pandacore.repository.UserRepositoryCustom;
import com.fredastone.security.JwtUserFactory;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryCustom userRepository;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final LoginUser user = userRepository.getLoginUser(username);  

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
        	
            return JwtUserFactory.create(user);
        }
    }
}
