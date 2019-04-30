package com.fredastone.pandacore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.ApprovalReview;
import com.fredastone.pandacore.entity.Approver;
import com.fredastone.pandacore.service.ApprovalService;
import com.fredastone.security.JwtTokenUtil;

import lombok.Getter;

@RestController
@RequestMapping(path = "v1/approvals")
public class ApprovalsController {
	
	private ApprovalService approvalService;
	
	@Value("${jwt.header}")
	private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    

	@Autowired
	public ApprovalsController(ApprovalService approvalService) {
		// TODO Auto-generated constructor stub
		this.approvalService = approvalService;
	}
	

	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE,ROLE_SUPPORT"})
	@RequestMapping(path = "add/approvalreview", method = RequestMethod.POST)
	public ResponseEntity<?> addApprovalReview(@RequestBody ApprovalReview approvalReview){
		
		
		return ResponseEntity.ok(approvalService.addApprovalReview(approvalReview));
		
		
	}
	

	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE,ROLE_SUPPORT"})
	@RequestMapping(path = "get/approvalreview/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getApprovalReview(@Valid @PathVariable("id") String approvalId){
			
		return ResponseEntity.ok(approvalService.getAllApprovalReview(approvalId));
				
	}
	
	
	@RequestMapping(path = "user/approve/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> approveUser(@Valid @PathVariable("id") String approvalId,HttpServletRequest request){
			
	    final String userid = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
		return ResponseEntity.ok(approvalService.approveUser(approvalId, userid));
				
	}


	@RequestMapping(path = "approvers/{service}", method = RequestMethod.GET)
	public ResponseEntity<?> getServiceApprovers(@PathVariable("service") String service) {

		String approvalConfigCount = null;
		switch (service) {
		case "employee":
			approvalConfigCount = "EMPLOYEEAPPROVALCOUNT";
		case "agent":
			approvalConfigCount = "AGENTAPPROVALCOUNT";

		}

		return null;

	}
	
	
	 class Approvers{
		 
		@Getter
		private final String totalapprovers;
		@Getter
		private final List<Approver> approvers;
		
		public Approvers(String totalapprovers,List<Approver> approvers){
			this.totalapprovers = totalapprovers;
			this.approvers = approvers;
		}
		
		
	}

}
