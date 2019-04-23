package com.fredastone.pandacore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.Approval;
import com.fredastone.pandacore.repository.ApproverRepository;
import com.fredastone.pandacore.service.ApprovalService;

@Service
public class ApprovalServiceImpl implements ApprovalService {

	private ApproverRepository approvalDao;
	
	
	@Autowired
	public ApprovalServiceImpl(ApproverRepository approvalDao) {
		// TODO Auto-generated constructor stub
		this.approvalDao = approvalDao;
	}
	
	@Override
	public List<Approval> getApprovalByApprover(String approverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Approval> getApprovalByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Approval changeApprover(String previousApprover, String newApprover, String approvalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Approval approvalOperation(String approvalId) {
		// TODO Auto-generated method stub
		return null;
	}

}
