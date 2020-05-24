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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.constants.ServiceConstants;
import com.fredastone.pandacore.entity.ApprovalReview;
import com.fredastone.pandacore.entity.Approver;
import com.fredastone.pandacore.entity.VSaleApprovalReview;
import com.fredastone.pandacore.models.ApprovalModel;
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
    
    private static final String APPROVED = "approved";
    private static final String REJECTED = "rejected";

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
	
	@RequestMapping(path = "sale/approve/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> approveLeaseSale(@Valid @PathVariable("id") String saleId, 
			@RequestParam("approvestatus") String approvestatus,
			@RequestParam("reviewdescription") String reviewdescription,
			HttpServletRequest request){
		
		final String userId = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
		
		switch (approvestatus) {	
			case APPROVED:
				approvalService.approveLeaseSale(userId, saleId, reviewdescription, (short) ServiceConstants.ACCEPTED_APPROVAL);
				break;
			case REJECTED:
				approvalService.approveLeaseSale(userId, saleId, reviewdescription, (short) ServiceConstants.REJECTED_APPROVAL);
				break;			
		}
		
		return null;
	}
	
	@RequestMapping(path = "user/approve/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> approveUser(@Valid @PathVariable("id") String approvalId,HttpServletRequest request){
			
	    final String userid = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
		return ResponseEntity
				.ok(approvalService.approveUser(approvalId, userid));
				
	}
	
	@RequestMapping(path = "capex/approve", method = RequestMethod.POST)
	public ResponseEntity<?> approveCapex(@Valid @RequestBody ApprovalModel approvalModel, HttpServletRequest request){
		final String approverId = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
		System.out.println("user id"+ approverId);
		approvalModel.setApproverId(approverId);
		return ResponseEntity.ok(approvalService.approveCapex(approvalModel));
	}
	
	@RequestMapping(path = "opex/approve", method = RequestMethod.POST)
	public ResponseEntity<?> approveOpex(@Valid @RequestBody ApprovalModel approvalModel, HttpServletRequest request){
		final String approverId = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
		approvalModel.setApproverId(approverId);
		return ResponseEntity.ok(approvalService.approveOpex(approvalModel));
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
	
	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE,ROLE_SUPPORT,ROLE_AGENT"})
	@RequestMapping(path = "get/approvalreview/sale",params= {"agentid","saleid"}, method = RequestMethod.POST)
	public ResponseEntity<?> getApprovalReviewForSale(@RequestParam("agentid") String agentId, @RequestParam("saleid") String saleId){
		
		List<VSaleApprovalReview > result = approvalService.getSaleApprovalReviewByAgent(agentId, saleId);
		
		if(result == null || result.isEmpty())
			return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(result);		
		
	}
	
	/*@RequestMapping(path = "userrole/activate/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> approveUserRoles(@Valid @PathVariable("id") String approvalId, HttpServletRequest request){
		final String userid = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
		return ResponseEntity.ok(approvalService.approveUserRole(userid, approvalId));	
	}*/
	
	@RequestMapping(path = "userrole/deactivate/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateUserRole(@Valid @PathVariable("id") String approvalId, HttpServletRequest request){
		final String userid = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
		return ResponseEntity.ok("");
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
