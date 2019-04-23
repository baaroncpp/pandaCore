package com.fredastone.pandacore.service;

import java.util.List;

import com.fredastone.pandacore.entity.Approval;

public interface ApprovalService {
	
	List<Approval> getApprovalByApprover(String approverId);
	List<Approval> getApprovalByCategory(String category);
	Approval changeApprover(String previousApprover,String newApprover,String approvalId);
	Approval approvalOperation(String approvalId);
	

}
