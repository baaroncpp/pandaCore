package com.fredastone.pandacore.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.entity.Installation;
import com.fredastone.pandacore.entity.Product;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
import com.fredastone.pandacore.repository.EmployeeRepository;
import com.fredastone.pandacore.repository.InstallationRepository;
import com.fredastone.pandacore.repository.ProductsRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.repository.UserRoleRepository;
import com.fredastone.pandacore.service.InstallationService;
import com.fredastone.pandacore.service.StorageService;
import com.fredastone.pandacore.util.ServiceUtils;
import com.microsoft.applicationinsights.core.dependencies.apachecommons.io.FilenameUtils;


@Service
public class InstallationServiceImpl implements InstallationService{

	private InstallationRepository installDao;
	private CustomerMetaRepository customerMetaDao;
	private StorageService storageService;
	private ProductsRepository productsRepository;
	private EmployeeRepository employeeRepository;
	private UserRoleRepository userRoleRepository;
	private UserRepository userRepository;
		
	@Value("${homePhotosFolder}")
	private String homePhotosUploadFolder;
	
	@Autowired
	public InstallationServiceImpl(InstallationRepository installDao,
			CustomerMetaRepository customerMetaDao,
			StorageService storageService, 
			ProductsRepository productsRepository,
			EmployeeRepository employeeRepository,
			UserRoleRepository userRoleRepository,
			UserRepository userRepository) {
		
		this.installDao = installDao;
		this.customerMetaDao = customerMetaDao;
		this.storageService = storageService;
		this.productsRepository = productsRepository;
		this.employeeRepository = employeeRepository;
		this.userRoleRepository = userRoleRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public Installation makeNewInstallation(Installation installation) {
				
		int count = 0;
		
		Optional<Product> product = productsRepository.findBySerialNumber(installation.getDeviceserial());
		Optional<CustomerMeta> customerMeta = customerMetaDao.findById(installation.getCustomerid());		
		
		if(!product.isPresent()) {
			throw new RuntimeException("Installation product of serial number: "+installation.getDeviceserial()+" does not exist");
		}
		
		Optional<EmployeeMeta> employeeMeta = employeeRepository.findById(installation.getEmployeeid());
		
		if(!employeeMeta.isPresent()) {
			throw new RuntimeException("Installation user of ID: "+installation.getEmployeeid()+" does not exist");
		}
		
		List<UserRole> userRoles = userRoleRepository.findAllByUser(userRepository.findById(installation.getEmployeeid()).get());
		
		if(userRoles.isEmpty()) {
			throw new RuntimeException("Employee does not qualify for Installation operation");
		}else {
			
			for(UserRole object : userRoles) {
				if(object.getRole().getName().name().equals(RoleName.ROLE_ENGINEER.name())) {
					count = count + 1;
				}
			}
			
		}
		
		if(count <= 0) {
			throw new RuntimeException("Employee of ID: "+installation.getEmployeeid()+" is not an installation Engineer");
		}else {
			
			installation.setId(ServiceUtils.getUUID());
			installation.setCreatedon(new Date());
			
			return installDao.save(installation);
		}
	}

	@Override
	public Installation updateInstallation(Installation installation) {
		
		Optional<Installation> install = installDao.findById(installation.getId());
		
		if(!install.isPresent()) {
			throw new RuntimeException("Installation activity not found");
		}
		
		installation.setId(install.get().getId());
		installation.setCreatedon(install.get().getCreatedon());
		
		return installDao.save(installation);
	}
	
	@Transactional
	@Override
	public void uploadProductImage(MultipartFile file, RedirectAttributes redirectAttributes, String userId) {
		
		Optional<CustomerMeta> pd = customerMetaDao.findById(userId);
		
		if(!pd.isPresent())
			throw new ItemNotFoundException(userId);
    	
		
    	storageService.store(file,String.format("%s/%s.%s",homePhotosUploadFolder,
    			userId,FilenameUtils.getExtension(file.getOriginalFilename())));
    	
    	pd.get().setHousephotopath(String.format("%s.%s",userId,FilenameUtils.getExtension(file.getOriginalFilename())));
		
	}

	@Transactional
	@Override
	public Resource getProductImage(String userId) {

		Optional<CustomerMeta> pd = customerMetaDao.findById(userId);
		
		if(!pd.isPresent())
			throw new ItemNotFoundException(userId);
    	
    	
		if(pd.get().getHousephotopath().isEmpty())
			throw new ItemNotFoundException(userId);
		
        final Resource file = storageService.loadAsResource(String.format("%s/%s",homePhotosUploadFolder,pd.get().getHousephotopath()));
        
        return file;
	}

	@Override
	public Installation getInstallationById(String id) {
		Optional<Installation> install = installDao.findById(id);
		
		if(!install.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		
		return install.get();
	}

	@Override
	public Page<Installation> getAllInstallation(int page, int size, String sortBy, Direction sortOrder) {
		
		final String[] sorts = sortBy.split(",");
		final Pageable pageRequest = PageRequest.of(page, size,Sort.by(Direction.DESC,sorts));
		Page<Installation> allsorted = installDao.findAll(pageRequest);
	
		return allsorted;
	
	}

	@Override
	public Installation finishInstallation(String installationId) {
		
		Optional<Installation> installation = installDao.findById(installationId);
		
		if(!installation.isPresent()) {
			throw new ItemNotFoundException(installationId);
		}
		
		installation.get().setEndtime(new Date());
		
		return installation.get();
	}

}
