package com.fredastone.pandacore.service.impl;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.azure.IAzureOperations;
import com.fredastone.pandacore.models.UploadLinks;
import com.fredastone.pandacore.service.UploadLinksService;

@Service
public class UploadLinksServiceImpl implements UploadLinksService {

	private IAzureOperations azureOperations;
	
	@Autowired
	 public UploadLinksServiceImpl(IAzureOperations azureOperations) {
		this.azureOperations = azureOperations;
	}
	
	@Override
	public UploadLinks getEmployeeUploadLinks(String userId) throws InvalidKeyException, MalformedURLException {
		
		UploadLinks links = new UploadLinks();
		links.setProfilepath(azureOperations.uploadProfile(userId));
		links.setIdcopypath(azureOperations.uploadIdCopy(userId));
		return links;
	}

	@Override
	public UploadLinks getCustomerUploadLinks(String userId) throws InvalidKeyException, MalformedURLException {
		
		UploadLinks links = new UploadLinks();
		links.setProfilepath(azureOperations.uploadProfile(userId));
		links.setIdcopypath(azureOperations.uploadIdCopy(userId));
		links.setConsentformpath(azureOperations.uploadCustConsent(userId));
		links.setHousephotopath(azureOperations.uploadHousePhotoPath(userId));

		return links;
	
	}

	@Override
	public UploadLinks getAgentUploadLinks(String userId) throws InvalidKeyException, MalformedURLException {
		
		UploadLinks links = new UploadLinks();
		links.setProfilepath(azureOperations.uploadProfile(userId));
		links.setIdcopypath(azureOperations.uploadIdCopy(userId));
		links.setContractpath(azureOperations.uploadAgentContract(userId));
		links.setCoipath(azureOperations.uploadAgentCertIncorp(userId));
		
		return links;
	}
	
	

}
