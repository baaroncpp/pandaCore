package com.fredastone.pandacore.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.constants.AgentUploadType;

public interface AgentService {
	

	public void uploadMetaInfo(MultipartFile file, RedirectAttributes redirectAttributes, String agentId,
			AgentUploadType uploadType);
	Resource getUploadedMetaInfo(String agentId, AgentUploadType uploadType);
}
