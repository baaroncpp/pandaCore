package com.fredastone.pandacore.pandacore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.pandacore.models.PaymentCompleted;
import com.fredastone.pandacore.pandacore.models.PaymentCompletedResponse;
import com.fredastone.pandacore.pandacore.models.RollbackRequest;
import com.fredastone.pandacore.pandacore.models.RollbackResponse;
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
	
	@RequestMapping(method = RequestMethod.POST,value = "/completemtnpayment",produces="application/json",consumes="application/json")
    public @ResponseBody
    ResponseEntity<PaymentCompletedResponse> completeThirdPartyPayment(@RequestBody PaymentCompleted meterNumber) {

        return operationService.paymentCompleted(meterNumber);
        
    }  
	
	
	@RequestMapping(method = RequestMethod.POST,value = "/rollback",produces="application/json",consumes="application/json")
    public @ResponseBody
    ResponseEntity<RollbackResponse> rollbackTransaction( @RequestBody RollbackRequest request) {

        return operationService.rollback(request);
        
    }  
	
	

}
