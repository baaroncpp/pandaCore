package com.fredastone.pandacore.controller;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.service.UploadLinksService;

//TODO Validate id sending requests
@RestController
@RequestMapping("v1/uploadlink")
public class UploadLinksController {
	
	private UploadLinksService operations;
	
	@Autowired
	public UploadLinksController(UploadLinksService operations) {
		
		this.operations = operations;
		
	}
	
    @RequestMapping(path="employee/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeeUploadLinks(@PathVariable("id") String id) throws InvalidKeyException, MalformedURLException{
    	
        return ResponseEntity.ok(operations.getEmployeeUploadLinks(id));
    }
   
    @RequestMapping(path="customer/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerUploadLinks(@PathVariable("id") String id) throws InvalidKeyException, MalformedURLException{
        return ResponseEntity.ok(operations.getCustomerUploadLinks(id));
    }
    
    @RequestMapping(path="agent/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getAgentUploadLinks(@PathVariable("id") String id) throws InvalidKeyException, MalformedURLException{
        return ResponseEntity.ok(operations.getAgentUploadLinks(id));
    }
    
    @RequestMapping(path="product/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getProductUploadLink(@PathVariable("id") String id) throws InvalidKeyException, MalformedURLException{
    	return ResponseEntity.ok(operations.getProductUploadLinks(id));
    }
    

}
