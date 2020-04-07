package com.fredastone.pandacore.service;

import java.awt.print.Pageable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fredastone.pandacore.constants.CustomerUploadType;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.models.CustomerModel;
import com.fredastone.pandacore.models.FileResponse;
import com.microsoft.azure.storage.StorageException;

public interface CustomerService {
	
	CustomerMeta addCustomerMeta(CustomerMeta customerMeta);
	CustomerMeta updateCustomerMeta(CustomerMeta customerMeta);
	Optional<User> getCustomer(String id);
	CustomerMeta getCustomerMeta(String id);
	Page<CustomerMeta> getAllCustomerMeta(int page, int count, String sortBy, Direction sortOrder);

	//Page<User> getAllActiveCustomers(boolean status,int size,int page);
	
	FileResponse uploadMetaInfo(MultipartFile file,  RedirectAttributes redirectAttributes,String customerId,CustomerUploadType customerUploadType) throws InvalidKeyException, MalformedURLException, URISyntaxException, IOException, StorageException;
	
	Resource getUploadedMetaInfo(String customerId,CustomerUploadType uploadType);
	
	CustomerMeta addMobileCustomer(CustomerModel customerModel) throws InvalidKeyException, MalformedURLException;
	

}
