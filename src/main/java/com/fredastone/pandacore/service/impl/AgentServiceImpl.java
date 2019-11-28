package com.fredastone.pandacore.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.azure.IAzureOperations;
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
	private IAzureOperations azureOperations;
	private ConfigRepository configDao;
	private AgentMetaRepository agentMetaDao;
	private StorageService storageService;
	
	@Autowired
	public AgentServiceImpl(UserRepository userDao,UserRoleRepository userRoleDao,ConfigRepository configDao,
			AgentMetaRepository agentMetaDao,IAzureOperations azureOperations,
			StorageService storageService) {
		// TODO Auto-generated constructor stub
		this.userDao = userDao;
		this.azureOperations = azureOperations;
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
		Optional<AgentMeta> agent = agentMetaDao.findByUserid(agentMeta.getUserid());
		
		if(!user.isPresent() || !user.get().getUsertype().equals(UserType.AGENT.name())) {
			throw new RuntimeException("User not found or user does not match type Agent");
		}else if(agent.isPresent()){
			throw new RuntimeException("Record of this user already exists");
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

	
	@Override
	public AgentMeta getAgentByEmail(String email) {
		Optional<User> user = userDao.findByEmail(email);
		AgentMeta agentMeta = getAgentByUserId(user.get().getId());
		
		if(agentMeta == null) {
			throw new RuntimeException("Agent not found");
		}
		return agentMeta;
	}
	
	public AgentMeta getAgentByUserId(String agentId) {
		Optional<AgentMeta> agent = agentMetaDao.findByUserid(agentId);
		
		if(!agent.isPresent()){
			throw new RuntimeException("Agent not found");
		}
		
		AgentMeta m = agent.get();
		m.getUser().setCoipath(azureOperations.getAgentCertIncorp(agentId));
		m.getUser().setProfilepath(azureOperations.getProfile(agentId));
		m.getUser().setIdcopypath(azureOperations.getIdCopy(agentId));
		
		return m;
	}

	@Override
	public AgentMeta activateAgent(String agentId) {
		
		AgentMeta agentMeta = getAgentByUserId(agentId);
		if(agentMeta != null) {
			
			agentMeta.setActivatedon(new Date());
			agentMeta.setIsactive(true);
			agentMetaDao.save(agentMeta);
			return getAgentByUserId(agentId);
		}else {
			throw new RuntimeException("Agent not found");
		}
	}

	@Override
	public AgentMeta deactivateAgent(String agentId) {
		//add to logs and events
		
		AgentMeta agentMeta = getAgentByUserId(agentId);
		if(agentMeta != null) {
			agentMeta.setDeactivatedon(new Date());
			agentMeta.setIsactive(false);
			agentMetaDao.save(agentMeta);
			return getAgentByUserId(agentId);
		}else {
			throw new RuntimeException("Agent not found");
		}
	}

	@Override
	public AgentMeta terminate(String agentId) {/*
		AgentMeta agentMeta = getAgentByUserId(agentId);
		if(agentMeta != null) {
			agentMeta.setTerminatedon(new Date());
			agentMeta.setIsterminated(true);
			agentMetaDao.save(agentMeta);
			return getAgentByUserId(agentId);
		}else {
			throw new RuntimeException("Agent not found");
		}*/
		return null;
	}
	
	public void replaceFile(String filename, MultipartFile file) {
		storageService.replaceFile(file, filename);
	}

	@Override
	public Page<AgentMeta> findAllAgents(Pageable pageable) {
		return agentMetaDao.findAll(pageable);
	}


	@Override
	public Page<AgentMeta> getAllAgentsByActive(Pageable pageable, boolean isactive) {
		
		Page<AgentMeta> activated = agentMetaDao.findByIsactiveTrue(pageable);
		Page<AgentMeta> notActivated = agentMetaDao.findByIsactiveFalse(pageable);
		
		if(activated.isEmpty() &&  notActivated.isEmpty()){
			throw new RuntimeException("No Values");
		}
		
		if(isactive == true) {
			return activated;
		}else{
			return notActivated;
		}
	}


	@Override
	public AgentMeta getAgentByPhoneNumber(String phoneNumber) {
		
		Optional<User> user = userDao.findByPrimaryphone(phoneNumber);
		
		if(!user.isPresent()) {
			throw new RuntimeException("User not found");
		}
		AgentMeta agentMeta = getAgentByUserId(user.get().getId());
		
		if(agentMeta == null) {
			throw new RuntimeException("Agent not found");
		}
		return agentMeta;
		
	}

	@Override
	public AgentMeta updateAgentMeta(AgentMeta agentaMeta) {
		
		Optional<AgentMeta> meta = agentMetaDao.findById(agentaMeta.getUserid());
		
		if(!meta.isPresent()) {
			throw new ItemNotFoundException(agentaMeta.getUserid());
		}
		return agentMetaDao.save(agentaMeta);
	}

}
