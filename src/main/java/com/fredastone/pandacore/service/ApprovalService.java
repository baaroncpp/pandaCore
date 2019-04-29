package com.fredastone.pandacore.service;

import java.util.List;

import com.fredastone.pandacore.entity.ApprovalReview;
import com.fredastone.pandacore.entity.User;

public interface ApprovalService {
	
	ApprovalReview addApprovalReview(ApprovalReview approvalReview);
	List<ApprovalReview> getAllApprovalReview(String id);
	
	User approveUser(String userId,String approverId);

}
