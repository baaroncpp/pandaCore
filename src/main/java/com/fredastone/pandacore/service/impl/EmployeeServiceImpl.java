package com.fredastone.pandacore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.ApproverRepository;
import com.fredastone.pandacore.repository.ConfigRepository;
import com.fredastone.pandacore.repository.EmployeeRepository;
import com.fredastone.pandacore.repository.VEmployeeApprovalRepository;
import com.fredastone.pandacore.service.EmployeeService;
import com.fredastone.pandacore.service.StorageService;
import com.microsoft.applicationinsights.core.dependencies.apachecommons.io.FilenameUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Value("${employeeapprovalcount}")
	private String employeeApprovalCountVar;
	
	@Value("${employeeapprovalroles}")
	private String employeeApprovalRolesVar;
	
	@Value("${employeeapprovalrolesseparator}")
	private String roleSeparator;
	
	
	@Value("${employeephotosfolder}")
	private String employeePhotoFolder;
	
	private StorageService storageService;
	
	private final EmployeeRepository employeeDao;
	private final ApproverRepository approvalsDao;
	
	private final ConfigRepository configDao;
	private final VEmployeeApprovalRepository vapprovalsDao;
	


	
	@Autowired
	 public EmployeeServiceImpl(EmployeeRepository employeeDao,ApproverRepository approvalDao,
			 ConfigRepository configDao,VEmployeeApprovalRepository vapprovalDao,StorageService storageService) {
		// TODO Auto-generated constructor stub
		this.employeeDao = employeeDao;
		this.approvalsDao = approvalDao;
		this.configDao = configDao;
		this.vapprovalsDao = vapprovalDao;
		this.storageService = storageService;
	}



	@Override
	public Optional<EmployeeMeta> findEmployeeById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<EmployeeMeta> findEmployeeByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<EmployeeMeta> findEmployeeByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<EmployeeMeta> findEmployeeByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public Page<EmployeeMeta> findAllEmployees(int page,int size){
		
		return employeeDao.findAll(PageRequest.of(page, size, Sort.by("createon").descending()));	
	}

	@Override
	public void uploadProfilePhoto(MultipartFile file, RedirectAttributes redirectAttributes, String employeeId) {
		Optional<EmployeeMeta> pd = employeeDao.findById(employeeId);
		
		if(!pd.isPresent())
			throw new ItemNotFoundException(employeeId);
    	
        
    	storageService.store(file,String.format("%s/%s.%s",employeePhotoFolder,
    			employeeId,FilenameUtils.getExtension(file.getOriginalFilename())));
    	
    	pd.get().setProfilepath(String.format("%s.%s",employeeId,FilenameUtils.getExtension(file.getOriginalFilename())));
		
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
	
}
