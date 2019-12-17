package com.fredastone.pandacore.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fredastone.pandacore.constants.AgentUploadType;
import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.models.FileResponse;
import com.microsoft.azure.storage.blob.StorageException;

public interface AgentService {
	
	AgentMeta addAgentMeta(AgentMeta agentMeta);
	
	AgentMeta updateAgentMeta(AgentMeta agentaMeta);
	
	public AgentMeta getAgentByUserId(String agentId);
	
	public FileResponse uploadMetaInfo(MultipartFile file, RedirectAttributes redirectAttributes, String agentId, AgentUploadType uploadType) throws InvalidKeyException, MalformedURLException, URISyntaxException, StorageException, IOException, com.microsoft.azure.storage.StorageException;
	
	Resource getUploadedMetaInfo(String agentId, AgentUploadType uploadType);
	
	void replaceFile(String filename, MultipartFile file);
	
	public Page<AgentMeta> getAllAgentsByActive(Pageable pageable, boolean isactive);
	
	AgentMeta getAgentByPhoneNumber(String phoneNumber);
	
	AgentMeta getAgentByEmail(String email);

	AgentMeta activateAgent(String agentId);
	
	AgentMeta deactivateAgent(String agentId);
	
	public Page<AgentMeta> findAllAgents(Pageable pageable);

}
