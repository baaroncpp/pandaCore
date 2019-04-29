package com.fredastone.pandacore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.ApprovalReview;
import com.fredastone.pandacore.entity.Approver;
import com.fredastone.pandacore.service.ApprovalService;

import lombok.Getter;

@RestController
@RequestMapping(path = "v1/approvals")
public class ApprovalsController {
	
	private ApprovalService approvalService;

	@Autowired
	public ApprovalsController(ApprovalService approvalService) {
		// TODO Auto-generated constructor stub
		this.approvalService = approvalService;
	}
	
	
	@RequestMapping(path = "add/approvalreview", method = RequestMethod.POST)
	public ResponseEntity<?> addApprovalReview(@RequestBody ApprovalReview approvalReview){
		
		
		return ResponseEntity.ok(approvalService.addApprovalReview(approvalReview));
		
		
	}
	
	@RequestMapping(path = "get/approvalreview/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getApprovalReview(@Valid @PathVariable("id") String approvalId){
			
		return ResponseEntity.ok(approvalService.getAllApprovalReview(approvalId));
				
	}
	
	
	@RequestMapping(path = "user/approve/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> approveUser(@Valid @PathVariable("id") String approvalId){
			
		
		return ResponseEntity.ok(approvalService.approveUser(approvalId, "fc179a74c902420bba3d16dfef1522af"));
				
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
