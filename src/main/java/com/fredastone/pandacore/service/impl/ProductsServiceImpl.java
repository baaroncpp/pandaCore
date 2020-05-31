package com.fredastone.pandacore.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.azure.IAzureOperations;
import com.fredastone.pandacore.entity.Product;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.exception.ProductNotFoundException;
import com.fredastone.pandacore.models.FileResponse;
import com.fredastone.pandacore.repository.ProductsRepository;
import com.fredastone.pandacore.service.ProductsService;
import com.fredastone.pandacore.service.StorageService;
import com.fredastone.pandacore.util.ServiceUtils;
import com.microsoft.applicationinsights.core.dependencies.apachecommons.io.FilenameUtils;
import com.microsoft.azure.storage.StorageException;

@Service
public class ProductsServiceImpl implements ProductsService {
	
	private ProductsRepository productDao;
	private StorageService storageService;
	
	@Value("${productsphotosfolder}")
	private String productsThumbnailFolder;
	
	private IAzureOperations azureOperations;
	
	@Autowired
	public ProductsServiceImpl(ProductsRepository productDao,StorageService storageService, IAzureOperations azureOperations) {
		// TODO Auto-generated constructor stub
		this.productDao = productDao;
		this.storageService = storageService;
		this.azureOperations = azureOperations;
	}

	@Override
	public Product addProduct(Product p) {
		// TODO Auto-generated method stub
		Optional<Product> product = productDao.findByName(p.getName());
		
		if(product.isPresent()) {
			throw new RuntimeException("Product with name "+p.getName()+" exists");
		}
		
		p.setId(ServiceUtils.getUUID());
		return productDao.save(p);
	}

	@Override
	public Optional<Product> getProduct(String id) {
		
		Optional<Product> product = productDao.findById(id);
		
		if(!product.isPresent()) {
			throw new ProductNotFoundException(id);
		}
		return productDao.findById(id);
	}

	@Override
	public Optional<Product> getProductByName(String name) {
		
		Optional<Product> product = productDao.findByName(name);
		
		if(!product.isPresent()) {
			throw new ProductNotFoundException(name);
		}
		return productDao.findByName(name);
	}

	@Override
	public Iterable<Product> getAllProducts() throws InvalidKeyException, MalformedURLException {
		
		List<Product> result = new ArrayList<>();
		
		for(Product object : productDao.findAll()) {
			object.setThumbnail(azureOperations.uploadProuctPicture(object.getId()));
			result.add(object);
		}		
		return result;
	}

	@Override
	public Product updateProduct(Product p) {
		// TODO Auto-generated method stub
		
		Optional<Product> pro = productDao.findById(p.getId());
		if(!pro.isPresent()) {
			throw new ProductNotFoundException(p.getId());
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
	public FileResponse uploadProductImage(MultipartFile file, RedirectAttributes redirectAttributes, String productId) throws URISyntaxException, IOException, StorageException, InvalidKeyException {
		
		Optional<Product> pd = productDao.findById(productId);
		
		if(!pd.isPresent())
			throw new ItemNotFoundException(productId);
		
		String finalFilePath = azureOperations.uploadProuctPicture(productId);
    	/*        
    	storageService.store(file,String.format("%s/%s.%s",productsThumbnailFolder,	productId,FilenameUtils.getExtension(file.getOriginalFilename())));
    	
    	pd.get().setThumbnail(String.format("%s.%s",productId,FilenameUtils.getExtension(file.getOriginalFilename())));
		*/
    	return azureOperations.uploadToAzure(file,finalFilePath);
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

	@Override
	public Product getProductBySerial(String serial) {
		
		Optional<Product> product = productDao.findBySerialNumber(serial);
		
		if(!product.isPresent()) {
			throw new RuntimeException("Product with serial number: "+serial+" does not exist");
		}
		
		return product.get();
	}

}
