package com.fredastone.pandacore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.AndroidTokens;
import com.fredastone.pandacore.repository.AndroidTokenRepository;

@RestController
@RequestMapping(path="v1/android")
public class AndroidNotificationsController {

	private AndroidTokenRepository repository;
	
	@Autowired
	public  AndroidNotificationsController(AndroidTokenRepository repository) {
		// TODO Auto-generated constructor stub
		this.repository  = repository;
	}
	
	//TODO Use id from authentication object
	@RequestMapping(path="/token/new",method = RequestMethod.POST)
    public ResponseEntity<?> postNewDirectSale(@RequestBody AndroidTokens token) {
		
		return ResponseEntity.ok(repository.save(token));
		
    }
	
	@RequestMapping(path="/token/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> postNewDirectSale(@PathVariable("id") String userid) {
		
		return ResponseEntity.ok(repository.findById(userid));
		
    }
	
	
}
