package com.fredastone.pandacore.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fredastone.pandacore.azure.IAzureOperations;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.Config;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.models.FileResponse;
import com.fredastone.pandacore.repository.ConfigRepository;
import com.fredastone.pandacore.repository.EmployeeRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.EmployeeService;
import com.fredastone.pandacore.service.StorageService;
import com.microsoft.applicationinsights.core.dependencies.apachecommons.io.FilenameUtils;
import com.microsoft.azure.storage.StorageException;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Value("${employeeapprovalcount}")
	private String employeeApprovalCountVar;
	
	@Value("${employeeapprovalroles}")
	private String employeeApprovalRolesVar;
	
	@Value("${employeeapprovalrolesseparator}")
	private String roleSeparator;
	
	
	@Value("${employeeapprover}")
	private String employeeApprover;
	
	@Value("${employeephotosfolder}")
	private String employeePhotoFolder;
	
	private StorageService storageService;
	
	private final EmployeeRepository employeeDao;
	private final UserRepository userDao;
	private final ConfigRepository configDao;
	private final IAzureOperations azureOperations;
	
	@Autowired
	 public EmployeeServiceImpl(EmployeeRepository employeeDao,StorageService storageService,UserRepository userDao,ConfigRepository configDao
			 ,IAzureOperations azureOperations) {
		// TODO Auto-generated constructor stub
		this.employeeDao = employeeDao;
		this.storageService = storageService;
		this.userDao = userDao;
		this.configDao = configDao;
		this.azureOperations = azureOperations;
	}

	@Override
	public EmployeeMeta findEmployeeById(String id) {
		// TODO Auto-generated method stub
		Optional<EmployeeMeta> meta =  employeeDao.findById(id);
		
		if(!meta.isPresent()){
			throw new RuntimeException("User not found");
		}
		
		EmployeeMeta m = meta.get();
		m.getUser().setProfilepath(azureOperations.getProfile(id));
		m.getUser().setIdcopypath(azureOperations.getIdCopy(id));
		
		return m;
	}

	@Override
	public Optional<EmployeeMeta> findEmployeeByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<EmployeeMeta> findEmployeeByMobile(String mobile) {
		
		Optional<EmployeeMeta> employeeMeta = employeeDao.findByMobile(mobile);
		
		if(!employeeMeta.isPresent()) {
			throw new ItemNotFoundException(mobile);
		}
		return employeeMeta;			
	}

	@Override
	public EmployeeMeta findEmployeeByEmail(String email) {
		
		Optional<User> user = userDao.findByEmail(email);
		
		if(!user.isPresent()) {
			throw new RuntimeException("User not found");
		}
		String userId = user.get().getId();
		
		return findEmployeeById(userId);
	}

	@Override
	public Page<EmployeeMeta> findAllEmployees(int page,int size){
		
		return employeeDao.findAll(PageRequest.of(page, size, Sort.by("createon").descending()));	
	}

	@Override
	public FileResponse uploadProfilePhoto(MultipartFile file, RedirectAttributes redirectAttributes, String employeeId) throws InvalidKeyException, URISyntaxException, IOException, StorageException {
		Optional<EmployeeMeta> pd = employeeDao.findById(employeeId);
		
		if(!pd.isPresent())
			throw new ItemNotFoundException(employeeId);
		
		String finalFilePath = azureOperations.uploadProfile(employeeId);
    	
        /*
    	storageService.store(file,String.format("%s/%s.%s",employeePhotoFolder,
    			employeeId,FilenameUtils.getExtension(file.getOriginalFilename())));
    	
    	pd.get().setProfilepath(String.format("%s.%s",employeeId,FilenameUtils.getExtension(file.getOriginalFilename())));
    	    	
    	String finalFileName = file.getOriginalFilename();
		String filePath = String.format("%s.%s",employeeId,FilenameUtils.getExtension(file.getOriginalFilename()));
    	*/
    	
    	return azureOperations.uploadToAzure(file, finalFilePath);
		
	}

	@Override
	public Resource getProfilePhoto(String employeeId) {

		Optional<EmployeeMeta> pd = employeeDao.findById(employeeId);
		
		if(!pd.isPresent())
			throw new ItemNotFoundException(employeeId);
    	
		if(pd.get().getProfilepath().isEmpty())
			throw new ItemNotFoundException(employeeId);
		
        final Resource file = storageService.loadAsResource(String.format("%s/%s",employeeId,pd.get().getProfilepath()));
        
        return file;
	}

	@Transactional
	@Override
	public EmployeeMeta addEmployee(EmployeeMeta employeemeta) {

		Optional<User> user = userDao.findById(employeemeta.getUserid());
		
		Optional<EmployeeMeta> empMeta = employeeDao.findById(employeemeta.getUserid());
		
		if(empMeta.isPresent()) {
			throw new RuntimeException("EmployeeMeta data for user: "+employeemeta.getUserid()+" already exists");
		}
		
		if(!user.isPresent() || !user.get().getUsertype().equals(UserType.EMPLOYEE.name())) {
			throw new RuntimeException("User not found or user does not match type employee");
		}		
		
		final EmployeeMeta newMeta = employeeDao.save(employeemeta);
		
		final Optional<Config> approverConfig = configDao.findByName(employeeApprover);
		if(approverConfig.isPresent()) {
			Optional<User> approver = userDao.findById(approverConfig.get().getValue());
			if(approver.isPresent()) {
				
				//TODO: Throw approval mail to queue to be sent to right administrator, might need to iterate emails if more than one can approve
			}
		}
		return newMeta;
	}

	@Override
	public EmployeeMeta updateEmployee(EmployeeMeta employeemeta) {
		
		Optional<EmployeeMeta> employee = employeeDao.findById(employeemeta.getUserid());
		
		if(!employee.isPresent()) {
			throw new RuntimeException("EmployeeMeta data not found");
		}
		return employeeDao.save(employeemeta);
	}
	
}
