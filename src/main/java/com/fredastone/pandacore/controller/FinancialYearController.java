package com.fredastone.pandacore.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.FinanceConfig;

@RestController
@RequestMapping(path="v1/fconfig")
public class FinancialYearController {
	
    @RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addConfig(@Valid @Required @RequestBody FinanceConfig config) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="get/{configName}",method = RequestMethod.GET)
    public ResponseEntity<?> getConfig(@PathParam("configName") String configName) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="get",method = RequestMethod.GET)
    public ResponseEntity<?> getAllConfigs() {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="update",method = RequestMethod.PUT)
    public ResponseEntity<?> updateConfig(@Valid @RequestBody FinanceConfig config) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteConfig(@Valid @RequestBody String configName) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    

}
