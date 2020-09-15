package com.fredastone.pandacore.azure;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.web.multipart.MultipartFile;

import com.fredastone.pandacore.models.FileResponse;
import com.microsoft.azure.storage.StorageException;

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
	 String getProductPicture(String productId)throws InvalidKeyException, MalformedURLException;
	 
	 String uploadEquipmentPicture(String equipmentId)  throws InvalidKeyException, MalformedURLException;
	 String getEquipmentPicture(String equipmentId)throws InvalidKeyException, MalformedURLException;
	 
	 String uploadReportFile(String reportId)throws InvalidKeyException, MalformedURLException;
	 String getReportFile(String reportId)throws InvalidKeyException, MalformedURLException;
	 
	 FileResponse uploadToAzure(MultipartFile file,String urlString) throws URISyntaxException,  IOException, StorageException;
	 
}
