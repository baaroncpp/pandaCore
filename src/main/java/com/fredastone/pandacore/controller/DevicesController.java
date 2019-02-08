package com.fredastone.pandacore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("v1/devices")
public class DevicesController {
	
	
	@RequestMapping(method = RequestMethod.GET)
	@Produces("application/json")
    public @ResponseBody
    ResponseEntity<List<String>> getDevices() {

       List<String> devs = new ArrayList<String>();
       devs.add("Test");
        
       
        return new ResponseEntity<List<String>>(devs, HttpStatus.OK);
        
  }    
    
	

}
