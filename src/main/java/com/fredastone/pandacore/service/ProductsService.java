package com.fredastone.pandacore.service;

import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.entity.Product;
import com.fredastone.pandacore.models.FileResponse;

public interface ProductsService {
	
	Product addProduct(Product p);
	Product updateProduct(Product p);
	Optional<Product> getProduct(String id);
	Optional<Product> getProductByName(String name);
	Iterable<Product> getAllProducts();
	FileResponse uploadProductImage(MultipartFile file,  RedirectAttributes redirectAttributes,String productId);
	Resource getProductImage(String productId);
	Product getProductBySerial(String serial);

}
