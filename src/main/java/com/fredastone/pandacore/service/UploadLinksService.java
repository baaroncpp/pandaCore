package com.fredastone.pandacore.service;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.util.Map;

import com.fredastone.pandacore.models.UploadLinks;

public interface UploadLinksService {

	public UploadLinks getEmployeeUploadLinks(String employeeId) throws InvalidKeyException, MalformedURLException;
	public UploadLinks  getCustomerUploadLinks(String customerId) throws InvalidKeyException, MalformedURLException;
	public UploadLinks getAgentUploadLinks(String agentId) throws InvalidKeyException, MalformedURLException;
	public Map<String, String> getProductUploadLinks(String productId)throws InvalidKeyException, MalformedURLException;
}
