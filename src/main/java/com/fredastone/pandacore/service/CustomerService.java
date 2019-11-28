package com.fredastone.pandacore.service;

import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fredastone.pandacore.constants.CustomerUploadType;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.models.FileResponse;

public interface CustomerService {
	
	CustomerMeta addCustomerMeta(CustomerMeta customerMeta);
	CustomerMeta updateCustomerMeta(CustomerMeta customerMeta);
	Optional<User> getCustomer(String id);
	CustomerMeta getCustomerMeta(String id);

	//Page<User> getAllActiveCustomers(boolean status,int size,int page);
	
	FileResponse uploadMetaInfo(MultipartFile file,  RedirectAttributes redirectAttributes,String customerId,CustomerUploadType customerUploadType);
	
	Resource getUploadedMetaInfo(String customerId,CustomerUploadType uploadType);

}
