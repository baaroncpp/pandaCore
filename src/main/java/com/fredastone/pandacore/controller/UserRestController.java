package com.fredastone.pandacore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.service.UserService;
import com.fredastone.security.JwtTokenUtil;
import com.fredastone.security.JwtUser;

@RestController
@RequestMapping("v1/user")
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }
    
    
    @RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@Valid @NotNull @RequestBody User user) {
    	
    	User u = userService.addUser(user);
        return new ResponseEntity<User>(u, HttpStatus.OK);
    }
    
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
    
    @RequestMapping(path="all/{state}",params = { "active", "direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(@Valid @PathVariable("state")String state,
    		@RequestParam("direction") String direction,@RequestParam("page") int page,@RequestParam("size") int size) {
    	
    	List<User> users = userService.getUsers(page, size, direction, state == "active");
        return ResponseEntity.ok(users);
    }
    

}
