package com.fredastone.pandacore.service.impl;

import java.awt.print.Pageable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fredastone.pandacore.azure.IAzureOperations;
import com.fredastone.pandacore.constants.CustomerUploadType;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.models.CustomerModel;
import com.fredastone.pandacore.models.FileResponse;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.CustomerService;
import com.fredastone.pandacore.service.StorageService;
import com.fredastone.pandacore.service.UserService;
import com.microsoft.azure.storage.StorageException;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Value("${customerUploadfolder}")
	private String customerUploadFolder;

	private CustomerMetaRepository customerMetaDao;
	private UserRepository userDao;
	private IAzureOperations azureOperations;
	private StorageService storageService;
	private UserService userService;

	@Autowired
	public CustomerServiceImpl( CustomerMetaRepository customerMetaDao,UserRepository userDao,
			StorageService storageService,IAzureOperations azureOperations, UserService userService) {
		// TODO Auto-generated constructor stub
		this.customerMetaDao = customerMetaDao;
		this.storageService = storageService;
		this.userDao = userDao;
		this.azureOperations = azureOperations;
		this.userService = userService;
	}


	@Override
	public CustomerMeta addCustomerMeta(CustomerMeta customerMeta) {
		
		final Optional<User> user = userDao.findById(customerMeta.getUserid());
		
		if(!user.isPresent() || !user.get().getUsertype().equals(UserType.CUSTOMER.name())) {
			throw new RuntimeException("User not found or user does not match type CUSTOMER");
		}
		
		return customerMetaDao.save(customerMeta);
		
	}

	@Transactional
	@Override
	public CustomerMeta addMobileCustomer(CustomerModel customerModel) throws InvalidKeyException, MalformedURLException {
		
		Optional<User> user = userDao.findByPrimaryphone(customerModel.getPrimaryphone());
		
		if(user.isPresent() && user.get().getUsertype().equals(UserType.CUSTOMER.toString())) {
			throw new RuntimeException("Phone number "+customerModel.getPrimaryphone()+" is used by another customer");
		}
		
		Optional<User> user1 = userDao.findByEmail(customerModel.getEmail());
		
		if(user1.isPresent() && user1.get().getUsertype().equals(UserType.CUSTOMER.toString())) {
			throw new RuntimeException("Email "+customerModel.getEmail()+" is used by another customer");
		}
		
		Optional<CustomerMeta> customerMeta = customerMetaDao.findByidnumber(customerModel.getIdnumber());
		
		if(customerMeta.isPresent()) {
			throw new RuntimeException("ID with number "+customerModel.getIdnumber()+" is used by another customer");
		}
		
		User user2 = new User();
		
		user2.setCompanyemail(customerModel.getCompanyemail());
		user2.setDateofbirth(customerModel.getDateofbirth());
		user2.setEmail(customerModel.getEmail());
		user2.setFirstname(customerModel.getFirstname());
		user2.setLastname(customerModel.getLastname());
		user2.setMiddlename(customerModel.getMiddlename());
		user2.setPassword(customerModel.getPassword());
		user2.setUsername(customerModel.getUsername());
		user2.setUsertype(customerModel.getUsertype().toString());
		user2.setPrimaryphone(customerModel.getPrimaryphone());
		
		CustomerMeta cust = new CustomerMeta();
		
		cust.setAddress(customerModel.getAddress());
		cust.setConsentformpath(customerModel.getConsentformpath());
		cust.setHomelat(customerModel.getHomelat());
		cust.setHomelong(customerModel.getHomelong());
		cust.setIdnumber(customerModel.getIdnumber());
		cust.setIdtype(customerModel.getIdtype());
		cust.setProfilephotopath("");
		cust.setSecondaryemail(customerModel.getSecondaryemail());
		cust.setSecondaryphone(customerModel.getSecondaryphone());
		cust.setVillageid(customerModel.getVillageid());
		
		cust.setUserid(userService.addUser(user2).getId());;
		
		return addCustomerMeta(cust);
	}

	
	@Override
	public Optional<User> getCustomer(String id) {
		
		Optional<User> customer = userDao.findCustomerById(id);
		
		if(!customer.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		return customer;
	}

//	@Override
//	public Page<Customer> getAllActiveCustomers(boolean status, int size, int page) {
//		// TODO Auto-generated method stub
//		return customerDao.findByisactive(status, PageRequest.of(page, size, Sort.Direction.ASC));
//	}

	
	@Override
	public FileResponse uploadMetaInfo(MultipartFile file, RedirectAttributes redirectAttributes, String customerId,
			CustomerUploadType uploadType) throws InvalidKeyException, URISyntaxException, IOException, StorageException {
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
		String filePath = "";

		switch (uploadType) {
		case PROFILE:
			//finalFileName = "profile_" + customerId;
			//meta.setProfilephotopath(String.format("%s.%s",finalFileName,FilenameUtils.getExtension(file.getOriginalFilename())));
			//filePath = meta.getProfilephotopath();
			filePath = azureOperations.uploadProfile(customerId);
			break;
		case ID_COPY:
			//finalFileName = "idcopy_" + customerId;
			//meta.setIdcopypath(String.format("%s.%s",finalFileName,FilenameUtils.getExtension(file.getOriginalFilename())));
			//filePath = meta.getIdcopypath();
			filePath = azureOperations.uploadIdCopy(customerId);
			break;
		case CONSENT_FORM:
			//finalFileName = "consentform_" + customerId;
			//meta.setConsentformpath(String.format("%s.%s",finalFileName,FilenameUtils.getExtension(file.getOriginalFilename())));
			//filePath = meta.getConsentformpath();
			filePath = azureOperations.uploadCustConsent(customerId);
			break;
		}

		//storageService.store(file, String.format("%s/%s.%s", customerUploadFolder, finalFileName, FilenameUtils.getExtension(file.getOriginalFilename())));

		
		customerMetaDao.save(meta);	
		
		return azureOperations.uploadToAzure(file, filePath);

	}

//	@Override
//	public Resource getUploadedMetaInfo(String customerId, CustomerUploadType uploadType) {
//
//		Optional<CustomerMeta> pd = customerMetaDao.findById(customerId);
//
//		if (!pd.isPresent())
//			throw new ItemNotFoundException(customerId);
//
//		switch (uploadType) {
//		case PROFILE:
//			if (pd.get().getProfilephotopath().isEmpty())
//				throw new ItemNotFoundException(customerId);
//
//			return storageService.loadAsResource(
//					String.format("%s/%s", customerUploadFolder, pd.get().getProfilephotopath()));
//		case ID_COPY:
//			if (pd.get().getIdcopypath().isEmpty())
//				throw new ItemNotFoundException(customerId);
//
//			return storageService.loadAsResource(
//					String.format("%s/%s", customerUploadFolder, pd.get().getIdcopypath()));
//
//		case CONSENT_FORM:
//			if (pd.get().getConsentformpath().isEmpty())
//				throw new ItemNotFoundException(customerId);
//
//			return storageService.loadAsResource(
//					String.format("%s/%s", customerUploadFolder, pd.get().getConsentformpath()));
//		default:
//			return null;
//
//		}
//
//	}

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
		Optional<CustomerMeta> cm = customerMetaDao.findById(id);
		
		if(!cm.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		
		CustomerMeta meta = cm.get();
		meta.getUser().setHousephotopath(azureOperations.getHousePhotoPath(id));
		meta.getUser().setProfilepath(azureOperations.getProfile(id));
		meta.getUser().setConsentformpath(azureOperations.getCustConsent(id));
		meta.getUser().setIdcopypath(azureOperations.getIdCopy(id));
		
		return meta;
	}

	@Override
	public CustomerMeta updateCustomerMeta(CustomerMeta customerMeta) {
		
		Optional<CustomerMeta> meta = customerMetaDao.findById(customerMeta.getUserid());
		
		if(!meta.isPresent()) {
			throw new ItemNotFoundException(customerMeta.getUserid());
		}
		
		return customerMetaDao.save(customerMeta);
	}


	@Override
	public List<CustomerMeta> getAllCustomerMeta(int page,int size,String sortBy,Direction sortOrder) {
		
		final org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size,Sort.by(sortOrder,sortBy));
		Page<CustomerMeta> custMeta = customerMetaDao.findAll(pageable);
		
		List<CustomerMeta> result = new ArrayList<>();
		
		for(CustomerMeta object : custMeta) {
			object.setProfilephotopath(azureOperations.getProfile(object.getIdnumber()));
			object.setConsentformpath(azureOperations.getCustConsent(object.getIdnumber()));
			object.setIdcopypath(azureOperations.getIdCopy(object.getIdnumber()));
			object.setHousephotopath(azureOperations.getHousePhotoPath(object.getIdnumber()));
			result.add(object);
		}
		// TODO Auto-generated method stub
		return result;
	}

}
