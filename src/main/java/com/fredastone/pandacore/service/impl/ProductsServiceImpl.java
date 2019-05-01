package com.fredastone.pandacore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.entity.Product;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.ProductsRepository;
import com.fredastone.pandacore.service.ProductsService;
import com.fredastone.pandacore.service.StorageService;
import com.fredastone.pandacore.util.ServiceUtils;
import com.microsoft.applicationinsights.core.dependencies.apachecommons.io.FilenameUtils;

@Service
public class ProductsServiceImpl implements ProductsService {
	
	private ProductsRepository productDao;
	private StorageService storageService;
	
	@Value("${productsphotosfolder}")
	private String productsThumbnailFolder;
	
	@Autowired
	public ProductsServiceImpl(ProductsRepository productDao,StorageService storageService) {
		// TODO Auto-generated constructor stub
		this.productDao = productDao;
		this.storageService = storageService;
	}

	@Override
	public Product addProduct(Product p) {
		// TODO Auto-generated method stub
		
		p.setId(ServiceUtils.getUUID());
		return productDao.save(p);
	}

	@Override
	public Optional<Product> getProduct(String id) {
		// TODO Auto-generated method stub
		return productDao.findById(id);
	}

	@Override
	public Optional<Product> getProductByName(String name) {
		// TODO Auto-generated method stub
		return productDao.findByName(name);
	}

	@Override
	public Iterable<Product> getAllProducts() {
		return productDao.findAll();
	}

	@Override
	public Product updateProduct(Product p) {
		// TODO Auto-generated method stub
		
		Optional<Product> pro = productDao.findById(p.getId());
		if(!pro.isPresent()) {
			throw new ItemNotFoundException(p.getId());
		}
		
		if(!p.getThumbnail().startsWith("http://")) {
			throw new IllegalAccessError("Url is not valid");
		}
		
		pro.get().setThumbnail(p.getThumbnail());
		pro.get().setDescription(p.getDescription());
		pro.get().setIsActive(p.getIsActive());
		pro.get().setName(p.getName());
		pro.get().setUnitcostselling(p.getUnitcostselling());
		
		productDao.save(pro.get());
		return pro.get();

	}

	@Override
	public void uploadProductImage(MultipartFile file, RedirectAttributes redirectAttributes, String productId) {
		Optional<Product> pd = productDao.findById(productId);
		
		if(!pd.isPresent())
			throw new ItemNotFoundException(productId);
    	
        
    	storageService.store(file,String.format("%s/%s.%s",productsThumbnailFolder,
    			productId,FilenameUtils.getExtension(file.getOriginalFilename())));
    	
    	pd.get().setThumbnail(String.format("%s.%s",productId,FilenameUtils.getExtension(file.getOriginalFilename())));
		
	}

	@Override
	public Resource getProductImage(String productId) {

		Optional<Product> pd = productDao.findById(productId);
		
		if(!pd.isPresent())
			throw new ItemNotFoundException(productId);
    	
		if(pd.get().getThumbnail().isEmpty())
			throw new ItemNotFoundException(productId);
		
        final Resource file = storageService.loadAsResource(String.format("%s/%s",productsThumbnailFolder,pd.get().getThumbnail()));
        
        return file;
	}

	
}