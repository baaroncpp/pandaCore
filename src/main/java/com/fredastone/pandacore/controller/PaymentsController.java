package com.fredastone.pandacore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.service.LeasePaymentService;
import com.fredastone.security.JwtTokenUtil;

@RestController
@RequestMapping("v1/payments")
public class PaymentsController {
	
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	LeasePaymentService leasePaymentService;
	
	@Autowired
	public PaymentsController(LeasePaymentService leasePaymentService) {
		this.leasePaymentService = leasePaymentService;
	}
	
    @RequestMapping(path="get",params = {"direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllPayments(@RequestParam("direction") String direction,@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseEntity.ok(leasePaymentService.getAllPayments(size, page, direction));
    }
    
    @RequestMapping(path="get/lease/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentsByLeaseId(@PathVariable("id")String leaseId){
    	return ResponseEntity.ok(leasePaymentService.getLeasePaymentsByLeaseId(leaseId));
    }
    
    @RequestMapping(path="get/customer/{id}",params = {"direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentsByCustomer(@PathVariable("id") String id,
    		@RequestParam("direction") String direction,@RequestParam("page") int page,@RequestParam("size") int size){
    	return ResponseEntity.ok(leasePaymentService.getpaymentByCustomerId(size, page, direction, id));
    }
    
    @RequestMapping(path="get/mobile",params = {"direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentsByAgent(HttpServletRequest request,
    		@RequestParam("direction") String direction,
    		@RequestParam("page") int page,
    		@RequestParam("size") int size){
    	String id = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
    	return ResponseEntity.ok(leasePaymentService.getLeasePaymentsByAgentId(id, size, page, direction));
    }
    
    /*@RequestMapping(path="get/deviceserial/{serial}",method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentsBySerial(@PathVariable("serial") String serial){
    	return ResponseEntity.ok(leasePaymentService.getLeasePaymentByDeviceSerial(serial));
    }*/
    
    @RequestMapping(path="update/leasepayment/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> updateLeasePayment(@PathVariable("id") String id, @RequestBody LeasePayment leasePayment){
    	return ResponseEntity.ok(leasePaymentService.updateLeasePayment(leasePayment));
    }

}
