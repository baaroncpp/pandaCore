package com.fredastone.pandacore.pandacore.controller;

import javax.ws.rs.Produces;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.pandacore.service.IOperationService;

@RestController
@RequestMapping("/account")
public class AccountsController {
	
	@Autowired
	private IOperationService operationService;
	
	@RequestMapping(method = RequestMethod.GET,value = "/getfri/{meter_number}",produces="application/json")
    public @ResponseBody
    ResponseEntity<String> getFinancialInformationForMobileMoney(@PathVariable("meter_number") String meterNumber) {

        return operationService.getMMFinancialInformation(meterNumber);
        
    }  

}
