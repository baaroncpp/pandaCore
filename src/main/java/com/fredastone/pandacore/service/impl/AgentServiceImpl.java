package com.fredastone.pandacore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.constants.AgentUploadType;
import com.fredastone.pandacore.constants.UserType;
//import com.fredastone.pandacore.entity.Agent;
import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.entity.Config;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.AgentMetaRepository;
import com.fredastone.pandacore.repository.ConfigRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.repository.UserRoleRepository;
import com.fredastone.pandacore.service.AgentService;
import com.fredastone.pandacore.service.StorageService;
import com.microsoft.applicationinsights.core.dependencies.apachecommons.io.FilenameUtils;


@Service
public class AgentServiceImpl implements AgentService{

	
	@Value("${agentpprovalcount}")
	private String agentApprovalCountVar;
	
	@Value("${agentapprovalroles}")
	private String agentApprovalRolesVar;
	
	@Value("${agentapprovalrolesseparator}")
	private String roleSeparator;
	
	@Value("${agentapprover}")
	private String agentApprover;
	
	
	@Value("${agentUploadFolder}")
	private String agentUploadFolder;

	
	private UserRepository userDao;
	private UserRoleRepository userRoleDao;
	private ConfigRepository configDao;
	private AgentMetaRepository agentMetaDao;
	private StorageService storageService;
	
	@Autowired
	public AgentServiceImpl(UserRepository userDao,UserRoleRepository userRoleDao,ConfigRepository configDao,
			AgentMetaRepository agentMetaDao,
			StorageService storageService) {
		// TODO Auto-generated constructor stub
		this.userDao = userDao;
		this.userRoleDao = userRoleDao;
		this.configDao = configDao;
		this.agentMetaDao = agentMetaDao;
		this.storageService = storageService;
	}


	@Override
	public void uploadMetaInfo(MultipartFile file, RedirectAttributes redirectAttributes, String agentId,
			AgentUploadType uploadType) {
		// TODO Auto-generated method stub
		Optional<AgentMeta> pd = agentMetaDao.findById(agentId);

		final AgentMeta meta;
		if (!pd.isPresent()) {
			
			Optional<User> user = userDao.findById(agentId);
			if(!user.isPresent())
				throw new ItemNotFoundException(agentId);
			
			meta = new AgentMeta();
			meta.setAgentcommissionrate(0);
			meta.setUser(user.get());
			
		}else {
			meta = pd.get();
		}
			
		String finalFileName = null;

		switch (uploadType) {
		case PROFILE:
			finalFileName = "profile_" + agentId;
			meta.setProfilepath(String.format("%s.%s",finalFileName,FilenameUtils.getExtension(file.getOriginalFilename())));
			break;
		case CONTRACT_DOC_PATH:
			finalFileName = "contract_" + agentId;
			meta.setContractdocpath(String.format("%s.%s",finalFileName,FilenameUtils.getExtension(file.getOriginalFilename())));
			break;
		}

		storageService.store(file, String.format("%s/%s.%s", agentUploadFolder, finalFileName,
				FilenameUtils.getExtension(file.getOriginalFilename())));

		
		agentMetaDao.save(meta);	

	}
	
	@Override
	public Resource getUploadedMetaInfo(String agentId, AgentUploadType uploadType) {

		Optional<AgentMeta> pd = agentMetaDao.findById(agentId);

		if (!pd.isPresent())
			throw new ItemNotFoundException(agentId);

		switch (uploadType) {
		case PROFILE:
			if (pd.get().getProfilepath().isEmpty())
				throw new ItemNotFoundException(agentId);

			return storageService.loadAsResource(
					String.format("%s/%s",agentUploadFolder, pd.get().getProfilepath()));
		case CONTRACT_DOC_PATH:
			if (pd.get().getContractdocpath().isEmpty())
				throw new ItemNotFoundException(agentId);

			return storageService.loadAsResource(
					String.format("%s/%s",agentUploadFolder, pd.get().getContractdocpath()));
	
		default:
			return null;

		}

	}


	@Override
	public AgentMeta addAgentMeta(AgentMeta agentMeta) {
		Optional<User> user = userDao.findById(agentMeta.getUserid());
		
		if(!user.isPresent() || !user.get().getUsertype().equals(UserType.AGENT.name())) {
			throw new RuntimeException("User not found or user does not match type employee");
		}
		
		
		final AgentMeta newMeta = agentMetaDao.save(agentMeta);
		
		final Optional<Config> approverConfig = configDao.findByName(agentApprover);
		if(approverConfig.isPresent()) {
			Optional<User> approver = userDao.findById(approverConfig.get().getValue());
			if(approver.isPresent()) {
				
				//TODO: Throw approval mail to queue to be sent to right administrator, might need to iterate emails if more than one can approve
			}
		}
		return newMeta;
	}

}
