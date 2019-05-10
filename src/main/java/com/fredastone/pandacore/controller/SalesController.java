package com.fredastone.pandacore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.service.SaleService;

@CrossOrigin("${crossoriginurl}")
@RestController
@RequestMapping("v1/sales")
public class SalesController {

	private SaleService saleService;
	
	@Autowired
	public SalesController(SaleService saleService) {
		// TODO Auto-generated constructor stubx
		this.saleService = saleService;
	}
	
	@RequestMapping(path="add/direct",method = RequestMethod.POST)
    public ResponseEntity<?> postNewDirectSale(@RequestBody Sale sale) {
		
		return ResponseEntity.ok(saleService.recoredNewDirectSale(sale));
		
    }
	
	@RequestMapping(path="add/lease",params = {"leaseoffer","agentid","customerid","cordlat","cordlong","deviceserial"},method = RequestMethod.POST)
    public ResponseEntity<?> postNewLeaseSale(@RequestParam("leaseoffer") int leaseId,@RequestParam("agentid") String agentid,
    		@RequestParam("customerid") String customerid,@RequestParam("cordlat") float cord_lat,
    		@RequestParam("cordlong") float cord_long,
    		@RequestParam("deviceserial") String deviceserial) {
		
		return ResponseEntity.ok(saleService.recoredNewLeaseSale(leaseId,agentid,customerid,cord_lat,cord_long,deviceserial));
		
    }
	
    @RequestMapping(path="get",params = {"page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(
    		@Valid @RequestParam("sortorder") 
    		Direction sortorder,
    		@Valid @RequestParam("sortby") 
    		String sortby,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam("size") int size) {
    	
    	return ResponseEntity.ok(saleService.getAllSales(page, size, sortby, sortorder));
    }
    
    @RequestMapping(path="get/unverified",params = {"agentid","page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllUnverifiedSales(
    		@Valid @RequestParam("agentid")
    		String agentid,
    		@Valid @RequestParam("sortorder") 
    		Direction sortorder,
    		@Valid @RequestParam("sortby") 
    		String sortby,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam("size") int size) {
    	
    	return ResponseEntity.ok(saleService.getUnverifiedleaseSale(agentid, page, size, sortby, sortorder));
    }
	
    
    @RequestMapping(path="get/verified",params = {"agentid","page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllVerifiedSales(
    		@Valid @RequestParam("agentid")
    		String agentid,
    		@Valid @RequestParam("sortorder") 
    		Direction sortorder,
    		@Valid @RequestParam("sortby") 
    		String sortby,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam("size") int size) {
    	
    	return ResponseEntity.ok(saleService.getVerifiedLeaseSale(agentid, page, size, sortby, sortorder));
    }
	
    
	@RequestMapping(path="complete/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> completeNewSale(@PathVariable("id") String sale) {
       return ResponseEntity.ok(saleService.completeSale(sale));
    }
	
}
