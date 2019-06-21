package com.fredastone.pandacore.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.constants.AgentUploadType;
import com.fredastone.pandacore.entity.AgentMeta;

public interface AgentService {
	

	AgentMeta addAgentMeta(AgentMeta agentMeta);
	AgentMeta getAgentById(String agentId);
	
	
	public void uploadMetaInfo(MultipartFile file, RedirectAttributes redirectAttributes, String agentId,
			AgentUploadType uploadType);
	Resource getUploadedMetaInfo(String agentId, AgentUploadType uploadType);
}
