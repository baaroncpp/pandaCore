package com.fredastone.pandacore.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fredastone.pandacore.entity.Role;
import com.fredastone.pandacore.service.RoleService;

@RestController
@RequestMapping(path = "v1/roles")
public class RoleController {
	
	RoleService roleService;
	
	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addRole(@Valid @NotNull @RequestBody Role role){
		return ResponseEntity.ok(roleService.addRole(role));
	}
	
	@RequestMapping(path="get/all",params = {"page","size"},method = RequestMethod.GET)
	public ResponseEntity<?> getRoles(@Valid @RequestParam("page") int page, @RequestParam("size") int size){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
		
		return ResponseEntity.ok(roleService.getRoles(pageable));
	}
	
	@RequestMapping(path="get/{id}",method = RequestMethod.GET)
	public ResponseEntity<?> getRole(@PathVariable String id){
		
		return ResponseEntity.ok(roleService.getRoleById(Short.valueOf(id)));
	}
	
}
