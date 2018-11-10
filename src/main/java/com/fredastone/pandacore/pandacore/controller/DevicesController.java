package com.fredastone.pandacore.pandacore.controller;

import java.util.List;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fredastone.pandacore.pandacore.repository.*;

import com.fredastone.pandacore.pandacore.entity.Devices;

@RestController
@RequestMapping("/devices")
public class DevicesController {
	
	@Autowired
	private IDeviceRespository deviceRepo;
	
	@RequestMapping(method = RequestMethod.GET)
	@Produces("application/json")
    public @ResponseBody
    ResponseEntity<List<Devices>> getDevices() {

       List<Devices> devs = deviceRepo.getAllDevice();
        
       
        return new ResponseEntity<List<Devices>>(devs, HttpStatus.OK);
        
    }    
    
	

}
