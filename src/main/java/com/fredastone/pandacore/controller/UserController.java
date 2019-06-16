package com.fredastone.pandacore.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.service.UserService;
import com.fredastone.security.JwtTokenUtil;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
    
    
    private static final String ALL_APPROVED_PATH = "approved";
    private static final String ALL_NOT_APPROVED_PATH = "notapproved";
    private static final String ALL_ACTIVE_PATH = "active";
    private static final String ALL_NOT_ACTIVE_PATH = "notactive";
    
    private static final String ALL_CUSTOMERS = "customer";
    private static final String ALL_EMPLOYEES = "employee";
    private static final String ALL_AGENTS = "agent";
    
    /*@RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }
    */
    
    
    
    
    @Secured({"ROLE_HR,ROLE_AGENT,ROLE_SUPPORT"})
    @RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@Valid @NotNull @RequestBody User user) {
    	
    	User u = userService.addUser(user);
        return new ResponseEntity<User>(u, HttpStatus.OK);
    }
    
    
    @PreAuthorize("hasRole(ROLE_HR) and hasRole(ROLE_MANAGER)")
    @RequestMapping(path="update/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@Valid @PathVariable("id")String id,@Valid @NotNull @RequestBody User user) {
    	User u = userService.updateUser(user);
    	return new ResponseEntity<User>(u, HttpStatus.OK);
    	
    }
    
    @RequestMapping(path="get/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@Valid @PathVariable("id")String  id) {
    	
    	User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @RequestMapping(path="get/username/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getUserByUsername(@Valid @PathVariable("id")String  id) {
    	
    	Optional<User> user = userService.getUserByUsername(id);
    	if(!user.isPresent())
    		return ResponseEntity.noContent().build();
    	
        return ResponseEntity.ok(user.get());
    }
    
    
    
    @Secured({"ROLE_HR,ROLE_MANAGER,ROLE_ADMIN,ROLE_SENIOR_MANAGER,ROLE_FINANCE,ROLE_MARKETING,ROLE_SUPPORT"})
    @RequestMapping(path="get/all/{type}",params = {"page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(
    		@PathVariable("type")
    		String type,
    		@Valid @RequestParam("sortorder") 
    		Direction sortorder,
    		@Valid @RequestParam("sortby") 
    		String sortby,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam("size") int size) {
    	
    	Page<User> users;
    	switch(type) {
    	case ALL_APPROVED_PATH:
    		users = userService.getAllForApproval(page, size, sortorder, sortby,Boolean.FALSE);
    		break;
    	case ALL_NOT_APPROVED_PATH:
    		users = userService.getAllForApproval(page, size, sortorder, sortby,Boolean.TRUE);
    		break;
    	case ALL_ACTIVE_PATH:
    		users = userService.getAllForActive(page, size, sortorder, sortby,Boolean.TRUE);
    		break;
    	case ALL_NOT_ACTIVE_PATH:
    		users = userService.getAllForActive(page, size, sortorder, sortby,Boolean.FALSE);
    		break;
    	case ALL_AGENTS:
    		users = userService.getAllByType(page, size, sortorder, sortby,UserType.AGENT);
    		break;
    	case ALL_CUSTOMERS:
    		users = userService.getAllByType(page, size, sortorder, sortby,UserType.CUSTOMER);
    		break;
    	case ALL_EMPLOYEES:
    		users = userService.getAllByType(page, size, sortorder, sortby,UserType.EMPLOYEE);
    		break;
    	default:
    		users = userService.getUsers(page, size, sortorder, sortby);
    		
    	}
    	
        return ResponseEntity.ok(users);
    }
    
    
}
