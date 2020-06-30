package com.fredastone.pandacore.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.models.LeaseOfferModel;
import com.fredastone.pandacore.service.LeaseOfferService;

@RestController
@RequestMapping("v1/leaseoffer")
public class LeaseOfferController {

	private LeaseOfferService loService;
	
	@Autowired
	public LeaseOfferController(LeaseOfferService loService) {
		// TODO Auto-generated constructor stub
		this.loService = loService;
	}
	

	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE"})
    @RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addLeaseOffer(@Valid @Required @RequestBody LeaseOfferModel leaseOffer) {
    		
        return ResponseEntity.ok(loService.addNewLeaseOffer(leaseOffer));
    }
    

	//@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE"})
    @RequestMapping(path="update",method = RequestMethod.PUT)
    public ResponseEntity<?> updateLeaseOffer(@Valid @Required @RequestBody LeaseOfferModel leaseOffer) {
        return ResponseEntity.ok(loService.updateLeaseOffer(leaseOffer));
    }
    
    @RequestMapping(path="get",method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeaseOffers() {
        return ResponseEntity.ok(loService.getAllLeaseOffers());
    }
    
    @RequestMapping(path="get/product/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getByProductId(@Valid @Required @PathVariable String productId) {
        return ResponseEntity.ok(loService.getLeaseOfferByProductId(productId));
    }
    
    @RequestMapping(path="get/id/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getByLeaseOfferId(@Valid @Required @PathVariable int id) {
        return ResponseEntity.ok(loService.getLeaseOffer(id));
    }
    
    @RequestMapping(path="test",method = RequestMethod.GET)
    public ResponseEntity<?> test() {
    	LeaseOfferModel lm = new LeaseOfferModel();
        return ResponseEntity.ok(lm);
    }
    
    
}
