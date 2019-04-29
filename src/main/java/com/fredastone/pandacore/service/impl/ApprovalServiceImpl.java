package com.fredastone.pandacore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.entity.ApprovalReview;
import com.fredastone.pandacore.entity.Approver;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.repository.AgentMetaRepository;
import com.fredastone.pandacore.repository.ApprovalReviewRepository;
import com.fredastone.pandacore.repository.ApproverRepository;
import com.fredastone.pandacore.repository.ConfigRepository;
import com.fredastone.pandacore.repository.EmployeeRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.ApprovalService;
import com.fredastone.pandacore.util.ServiceUtils;

@Service
public class ApprovalServiceImpl implements ApprovalService {

	private ApproverRepository approverDao;
	private ApprovalReviewRepository approvalReviewDao;
	private UserRepository userDao;
	private EmployeeRepository employeeDao;
	private AgentMetaRepository agentDao;
	private ConfigRepository configDao;
	
	private static final String CUSTOMER_USER_TYPE = "customer";
	private static final String EMPLOYEE_USER_TYPE = "employee";
	private static final String AGENT_USER_TYPE = "agent";
	
	@Autowired
	public ApprovalServiceImpl(
			ApprovalReviewRepository approvalReviewDao,
			UserRepository userDao,
			EmployeeRepository employeeDao,
			AgentMetaRepository agentDao,
			ConfigRepository configDao,
			ApproverRepository approverDao) {
		
		this.approvalReviewDao = approvalReviewDao;
		this.userDao = userDao;
		this.employeeDao = employeeDao;
		this.agentDao = agentDao;
		this.configDao = configDao;
		this.approverDao = approverDao;
		
	}
	

	@Override
	public User approveUser(String userId,String approverId) {
		Optional<User> user = this.userDao.findById(userId);
		
		if(!user.isPresent())
			throw new RuntimeException("User with id "+userId+" not found in system");
		
		if(user.get().isIsapproved() || user.get().getUsertype().toLowerCase().equals(CUSTOMER_USER_TYPE))
			throw new RuntimeException("Operation is not supported for user with id "+userId);

		
		user.get().setIsapproved(Boolean.TRUE);
		user.get().setIsactive(Boolean.TRUE);
		
		if(user.get().getUsertype().toLowerCase().equals(EMPLOYEE_USER_TYPE))
		{
			Optional<EmployeeMeta> employeeMeta = this.employeeDao.findById(userId);
			if(!employeeMeta.isPresent()) {
				throw new RuntimeException("No Employee additinal info found");
			}
			
			employeeMeta.get().setIsapproved(Boolean.TRUE);
			employeeMeta.get().setIsterminated(Boolean.FALSE);
			
			this.employeeDao.save(employeeMeta.get());
			
		}
		
		if(user.get().getUsertype().toLowerCase().equals(AGENT_USER_TYPE)) {
			Optional<AgentMeta> agentMeta = this.agentDao.findById(userId);
			if(!agentMeta.isPresent()) {
				throw new RuntimeException("No Agent additonal info found");
			}
			
			agentMeta.get().setIsactive(Boolean.TRUE);
			agentMeta.get().setIsdeactivated(Boolean.FALSE);
			
			agentDao.save(agentMeta.get());
		}
		
		
		Approver approver = Approver.builder().id(ServiceUtils.getUUID()).userid(approverId).itemapproved("user").itemid(user.get().getId()).build();
		
		approverDao.save(approver);
		
		
		return this.userDao.save(user.get());
	}



	@Override
	public ApprovalReview addApprovalReview(ApprovalReview approvalReview) {
		//TODO Add a check on approva
		
		approvalReview.setId(ServiceUtils.getUUID());
		return approvalReviewDao.save(approvalReview);
		
	}

	@Override
	public List<ApprovalReview> getAllApprovalReview(String id) {
		
		return approvalReviewDao.findAllByapprovalid(id);

	}


}
