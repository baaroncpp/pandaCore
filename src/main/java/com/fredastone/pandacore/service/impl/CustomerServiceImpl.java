package com.fredastone.pandacore.service.impl;

import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.constants.CustomerUploadType;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.CustomerService;
import com.fredastone.pandacore.service.StorageService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Value("${customerUploadfolder}")
	private String customerUploadFolder;

	private CustomerMetaRepository customerMetaDao;
	private UserRepository userDao;

	private StorageService storageService;

	@Autowired
	public CustomerServiceImpl( CustomerMetaRepository customerMetaDao,UserRepository userDao,
			StorageService storageService) {
		// TODO Auto-generated constructor stub
		this.customerMetaDao = customerMetaDao;
		this.storageService = storageService;
		this.userDao = userDao;
	}


	@Override
	public CustomerMeta addCustomerMeta(CustomerMeta customerMeta) {
		
		final Optional<User> user = userDao.findById(customerMeta.getUserid());
		
		if(!user.isPresent() || !user.get().getUsertype().equals(UserType.CUSTOMER.name())) {
			throw new RuntimeException("User not found or user does not match type employee");
		}
		
		
		return customerMetaDao.save(customerMeta);
		
	}

	@Override
	public Optional<User> getCustomer(String id) {
		// TODO Auto-generated method stub
		return userDao.findCustomerById(id);
	}

//	@Override
//	public Page<Customer> getAllActiveCustomers(boolean status, int size, int page) {
//		// TODO Auto-generated method stub
//		return customerDao.findByisactive(status, PageRequest.of(page, size, Sort.Direction.ASC));
//	}

	
	@Override
	public void uploadMetaInfo(MultipartFile file, RedirectAttributes redirectAttributes, String customerId,
			CustomerUploadType uploadType) {
		// TODO Auto-generated method stub
		Optional<CustomerMeta> pd = customerMetaDao.findById(customerId);

		final CustomerMeta meta;
		if (!pd.isPresent()) {
			
			Optional<User> user = userDao.findById(customerId);
			if(!user.isPresent())
				throw new ItemNotFoundException(customerId);
			
			meta = new CustomerMeta();
			meta.setUser(user.get());
			
		}else {
			meta = pd.get();
		}
			
		String finalFileName = null;

		switch (uploadType) {
		case PROFILE:
			finalFileName = "profile_" + customerId;
			meta.setProfilephotopath(String.format("%s.%s",finalFileName,FilenameUtils.getExtension(file.getOriginalFilename())));
			break;
		case ID_COPY:
			finalFileName = "idcopy_" + customerId;
			meta.setIdcopypath(String.format("%s.%s",finalFileName,FilenameUtils.getExtension(file.getOriginalFilename())));
			break;
		case CONSENT_FORM:
			finalFileName = "consentform_" + customerId;
			meta.setConsentformpath(String.format("%s.%s",finalFileName,FilenameUtils.getExtension(file.getOriginalFilename())));
			break;
		}

		storageService.store(file, String.format("%s/%s.%s", customerUploadFolder, finalFileName,
				FilenameUtils.getExtension(file.getOriginalFilename())));

		
		customerMetaDao.save(meta);	

	}

	@Override
	public Resource getUploadedMetaInfo(String customerId, CustomerUploadType uploadType) {

		Optional<CustomerMeta> pd = customerMetaDao.findById(customerId);

		if (!pd.isPresent())
			throw new ItemNotFoundException(customerId);

		switch (uploadType) {
		case PROFILE:
			if (pd.get().getProfilephotopath().isEmpty())
				throw new ItemNotFoundException(customerId);

			return storageService.loadAsResource(
					String.format("%s/%s", customerUploadFolder, pd.get().getProfilephotopath()));
		case ID_COPY:
			if (pd.get().getIdcopypath().isEmpty())
				throw new ItemNotFoundException(customerId);

			return storageService.loadAsResource(
					String.format("%s/%s", customerUploadFolder, pd.get().getIdcopypath()));

		case CONSENT_FORM:
			if (pd.get().getConsentformpath().isEmpty())
				throw new ItemNotFoundException(customerId);

			return storageService.loadAsResource(
					String.format("%s/%s", customerUploadFolder, pd.get().getConsentformpath()));
		default:
			return null;

		}

	}


	@Override
	public CustomerMeta getCustomerMeta(String id) {
		// TODO Auto-generated method stub
		Optional<CustomerMeta> cm =  customerMetaDao.findById(id);
		return cm.isPresent() == true ? cm.get() : null;
	}

}
