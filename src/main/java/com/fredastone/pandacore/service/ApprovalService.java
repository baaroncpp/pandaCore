package com.fredastone.pandacore.service;

import java.util.List;
import com.fredastone.pandacore.entity.ApprovalReview;
import com.fredastone.pandacore.entity.Capex;
import com.fredastone.pandacore.entity.Opex;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.entity.VSaleApprovalReview;
import com.fredastone.pandacore.models.ApprovalModel;

public interface ApprovalService {
	
	ApprovalReview addApprovalReview(ApprovalReview approvalReview);
	
	List<ApprovalReview> getAllApprovalReview(String id);
	
	User approveUser(String userId,String approverId);
	
	List<VSaleApprovalReview> getSaleApprovalReviewByAgent(String agentId, String saleId);
	
	//public UserRole approveUserRole(String approverId, String approvalId);
	
	//UserRole deactivateUserRole(String userRoleId, String approverId);
	
	User deactivateUser(String userId,String approverId);
	
	Sale approveLeaseSale(String userId, String saleId, String review, short saleStatus);
	
	Capex approveCapex(ApprovalModel approvalModel);
	
	Opex approveOpex(ApprovalModel approvalModel);
}
