package com.fredastone.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.entity.LoginUser;
import com.fredastone.pandacore.entity.Role;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.repository.UserRepositoryCustom;
import com.fredastone.pandacore.repository.UserRoleRepository;
import com.fredastone.security.JwtUserFactory;

@Transactional
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryCustom userRepositoryCustom;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserRoleRepository userRoleRepository;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //final LoginUser user = userRepositoryCustom.getLoginUser(username);  
    	final LoginUser user = getLoginUser(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
        	
            return JwtUserFactory.create(user);
        }
    }
    
    @Transactional
    private LoginUser getLoginUser(String username) {
    	
    	Optional<User> user = userRepository.findByUsername(username);
    	if(!user.isPresent()) {
    		throw new RuntimeException(username+" NOT FOUND");
    	}
    	
    	List<UserRole> userRoles = userRoleRepository.findAllByUser(user.get());
    	Set<Role> roles = new HashSet<>();

    	if(userRoles.isEmpty()) {
    		throw new RuntimeException("User registration incomplete");
    	}
    	
    	for(UserRole object : userRoles) {
    		roles.add(object.getRole());
    	}
    	
    	LoginUser loginUser = new LoginUser();
    	loginUser.setEmail(user.get().getEmail());
    	loginUser.setId(user.get().getId());
		loginUser.setIsactive(user.get().isIsactive());
		loginUser.setIsapproved(user.get().isIsapproved());
		loginUser.setPassword(user.get().getPassword());
		loginUser.setRolename(roles.iterator().next().getName().name());
		loginUser.setUsername(user.get().getUsername());
		loginUser.setRoles(new ArrayList<>(roles));
    	
    	return loginUser;
    }
}
