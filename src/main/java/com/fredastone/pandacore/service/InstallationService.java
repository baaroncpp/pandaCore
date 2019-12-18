package com.fredastone.pandacore.service;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.entity.Installation;

public interface InstallationService {

	Installation makeNewInstallation(Installation installation);
	Installation updateInstallation(Installation installation);
	
	Installation getInstallationById(String id);
	Page<Installation> getAllInstallation(int page,int size,String sortBy,Direction sortOrder);
	
	void uploadProductImage(MultipartFile file, RedirectAttributes redirectAttributes, String customerId);
	Resource getProductImage(String customerId);
	
	public Installation finishInstallation(String installationId);
	
}
