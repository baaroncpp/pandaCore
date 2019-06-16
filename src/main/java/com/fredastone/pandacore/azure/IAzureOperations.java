package com.fredastone.pandacore.azure;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;

public interface IAzureOperations {
	
	 String uploadProfile(String userId) throws InvalidKeyException, MalformedURLException;
	 String getProfile(String userId) ;
	 String uploadAgentContract(String agentId) throws InvalidKeyException, MalformedURLException;
	 String getAgentContract(String agentid);
	 String uploadAgentCertIncorp(String agentId) throws InvalidKeyException, MalformedURLException;
	 String getAgentCertIncorp(String agentId);
	 String uploadIdCopy(String userId) throws InvalidKeyException, MalformedURLException;
	 String getIdCopy(String userId);
	 String uploadCustConsent(String customerId) throws InvalidKeyException, MalformedURLException;
	 String getCustConsent(String customerId);
	 String uploadHousePhotoPath(String customerId) throws InvalidKeyException, MalformedURLException; 
	 String getHousePhotoPath(String customerId);
	 
	 String uploadProuctPicture(String productId)  throws InvalidKeyException, MalformedURLException;
	 String uploadEquipemntPicture(String equipmentId)  throws InvalidKeyException, MalformedURLException;
	 
}
