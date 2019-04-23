package com.fredastone.pandacore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.Approver;
import com.fredastone.pandacore.entity.Config;
import com.fredastone.pandacore.repository.ApproverRepository;
import com.fredastone.pandacore.repository.ConfigRepository;

import lombok.Getter;

@RestController
@RequestMapping(path = "v1/approvals")
public class ApprovalsController {

	private ApproverRepository approverDao;
	private ConfigRepository configDao;

	@Autowired
	public ApprovalsController(ApproverRepository approverDao,ConfigRepository configDao) {
		// TODO Auto-generated constructor stub
		this.approverDao = approverDao;
		this.configDao = configDao;
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

		Optional<Config> config = configDao.findByName(approvalConfigCount);

		if (!config.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		
		Config c = config.get();

		if(Integer.valueOf(c.getValue()) <1 )
		{
			return ResponseEntity.ok("{}");
		}
		
		
		// First check in configs if this service requires approvals
		List<Approver> approver = approverDao.findByApprovalservice(service);
		if (approver == null || approver.isEmpty())
			return ResponseEntity.status(500).build();

		return ResponseEntity.ok(new Approvers(c.getValue(),approver));

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
