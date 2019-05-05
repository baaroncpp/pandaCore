package com.fredastone.pandacore.service.impl;

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

import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.Installation;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
import com.fredastone.pandacore.repository.InstallationRepository;
import com.fredastone.pandacore.service.InstallationService;
import com.fredastone.pandacore.service.StorageService;
import com.fredastone.pandacore.util.ServiceUtils;
import com.microsoft.applicationinsights.core.dependencies.apachecommons.io.FilenameUtils;


@Service
public class InstallationServiceImpl implements InstallationService{

	private InstallationRepository installDao;
	private CustomerMetaRepository customerMetaDao;
	private StorageService storageService;
		
	@Value("${homePhotosFolder}")
	private String homePhotosUploadFolder;
	
	@Autowired
	public InstallationServiceImpl(InstallationRepository installDao,CustomerMetaRepository customerMetaDao,StorageService storageService) {
		// TODO Auto-generated constructor stub
		this.installDao = installDao;
		this.customerMetaDao = customerMetaDao;
		this.storageService = storageService;
	}
	
	@Override
	public Installation makeNewInstallation(Installation installation) {
		// TODO Auto-generated method stub
		
		installation.setId(ServiceUtils.getUUID());
		return installDao.save(installation);
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
		
		if(install.isPresent())
			return install.get();
		return null;
	}

	@Override
	public Page<Installation> getAllInstallation(int page, int size, String sortBy, Direction sortOrder) {
		
		final String[] sorts = sortBy.split(",");
		final Pageable pageRequest = PageRequest.of(page, size,Sort.by(Direction.DESC,sorts));
		Page<Installation> allsorted = installDao.findAll(pageRequest);
	
		return allsorted;
	
	}

}
