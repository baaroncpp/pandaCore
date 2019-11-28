package com.fredastone.pandacore.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fredastone.pandacore.service.UserRoleService;

@RestController
@RequestMapping(path = "v1/userroles")
public class UserRoleController {
	
	UserRoleService userRoleService;
	
	@Autowired
	public UserRoleController(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
	
	@RequestMapping(path="add",params = {"userId","roleId"},method = RequestMethod.GET)
	public ResponseEntity<?> addUserRole(@Valid @RequestParam("userId")String userId, @RequestParam("roleId")String roleId){
		return ResponseEntity.ok(userRoleService.addUserRole(userId, Short.valueOf(roleId)));		
	}
	
	public ResponseEntity<?> removeUserRole(String userId, String roleId){
		return null;		
	}
	
	@RequestMapping(path="get/user/{userid}",method = RequestMethod.GET)
	public ResponseEntity<?> getUserRoles(@PathVariable("userid") String userid){
		return ResponseEntity.ok(userRoleService.getUserRoles(userid));
	}
	
}
