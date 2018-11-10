package com.fredastone.pandacore.pandacore.service;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public interface IOperationService {
	
	ResponseEntity<String>  getMMFinancialInformation(String meterNumber);

}
