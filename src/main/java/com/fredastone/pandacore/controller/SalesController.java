package com.fredastone.pandacore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.fredastone.pandacore.entity.VLeaseSaleDetails;
import com.fredastone.pandacore.repository.VerificationRepository;
import com.fredastone.pandacore.service.SaleService;

@CrossOrigin("${crossoriginurl}")
@RestController
@RequestMapping("v1/sales")
public class SalesController {

	private SaleService saleService;
	private VerificationRepository verificationRepo;
	
	@Autowired
	public SalesController(SaleService saleService,VerificationRepository verificationRepo) {
		// TODO Auto-generated constructor stubx
		this.saleService = saleService;
		this.verificationRepo = verificationRepo;
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
    
    
    @RequestMapping(path="get/agent/{agentid}",params = {"page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> getallbyagent(
    		@PathVariable("agentid") String agentid,
    		@Valid @RequestParam("sortorder") 
    		Direction sortorder,
    		@Valid @RequestParam("sortby") 
    		String sortby,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam("size") int size) {
    	
    	return ResponseEntity.ok(saleService.getAllSalesByAgentId(agentid,page, size, sortby, sortorder));
    }
    
    
    @RequestMapping(path="get/lease/detail",params = {"page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeaseSaleDetail(
    		@RequestParam(required=false,defaultValue="DESC",name="sortorder") 
    		Direction sortorder,
    		@RequestParam(required=false,name="sortby",defaultValue="createdon") 
    		String sortby,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam(required=false,name="size",defaultValue="50") int size) {
    	
    	return ResponseEntity.ok(saleService.getAllLeaseSaleDetail(page, size, sortby, sortorder));
    }
    
    @RequestMapping(path="get/lease/detail",params = {"reviewstatus","page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeaseSaleByReviewStatus(
    		@Valid @RequestParam(name="reviewstatus",defaultValue="false") boolean reviewstatus,
    		@Valid @RequestParam(defaultValue="DESC",name="sortorder") 
    		Direction sortorder,
    		@Valid @RequestParam(name="sortby",defaultValue="createdon") 
    		String sortby,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam(name="size",defaultValue="10") int size)  {
    	
    	return ResponseEntity.ok(saleService.getAllLeaseSaleByReviewStatus(reviewstatus,page, size, sortby, sortorder));
    }
    
   
    
    
    @RequestMapping(path="get/lease/detail/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getLeaseSaleDetail(@PathVariable("id") String saleid) {
    	
    	final VLeaseSaleDetails lsd = saleService.getLeaseSaleDetail(saleid);
    	if(lsd == null)
    		return ResponseEntity.noContent().build();
    	return ResponseEntity.ok(lsd);
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
	
    
    @RequestMapping(path="get/unverified/withcustomer",params = {"agentid","page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllUnverifie(
    		@Valid @RequestParam("agentid")
    		String agentid,
    		@Valid @RequestParam("sortorder") 
    		Direction sortorder,
    		@Valid @RequestParam("sortby") 
    		String sortby,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam("size") int size) {
    	
 
		final Pageable pageRequest = PageRequest.of(page, size,Sort.by(sortorder,sortby));
    	return ResponseEntity.ok(verificationRepo.findAllUnverified(agentid, pageRequest));
    	
    }
    
    @RequestMapping(path="get/verified/withcustomer",params = {"agentid","page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> verifiedwithcustomer(
    		@Valid @RequestParam("agentid")
    		String agentid,
    		@Valid @RequestParam("sortorder") 
    		Direction sortorder,
    		@Valid @RequestParam("sortby") 
    		String sortby,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam("size") int size) {
    	

		final Pageable pageRequest = PageRequest.of(page, size,Sort.by(sortorder,sortby));
    	return ResponseEntity.ok(verificationRepo.findAllVerified(agentid, pageRequest));
    }
    
    
	@RequestMapping(path="complete/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> completeNewSale(@PathVariable("id") String sale) {
       return ResponseEntity.ok(saleService.completeSale(sale));
    }
	
}
