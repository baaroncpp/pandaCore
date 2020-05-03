package com.fredastone.pandacore.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fredastone.pandacore.entity.Product;
import com.fredastone.pandacore.service.ProductsService;
import com.microsoft.azure.storage.StorageException;

@RestController
@RequestMapping("v1/product")
public class ProductController {

	private ProductsService productService;

	@Autowired
	public ProductController(ProductsService productService) {
		// TODO Auto-generated constructor stub
		this.productService = productService;
	}

	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE"})
	@RequestMapping(path = "add", method = RequestMethod.POST)
	public ResponseEntity<?> addProductt(@RequestBody Product product) {

		return ResponseEntity.ok(productService.addProduct(product));
	}

	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE"})
	@RequestMapping(path = "update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduct(@RequestBody Product product) {

		return ResponseEntity.ok(productService.updateProduct(product));
	}

	
	@RequestMapping(path = "get", method = RequestMethod.GET)
	public ResponseEntity<?> getAllProducts() throws InvalidKeyException, MalformedURLException {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@RequestMapping(path = "get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductById(@PathVariable("id") String id) {

		return ResponseEntity.ok(productService.getProduct(id));
	}

	@RequestMapping(path = "get/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductByName(@PathVariable("name") String name) {
		return ResponseEntity.ok(productService.getProductByName(name));
	}


	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE"})
	@PostMapping(value = "/thumbnail/{id}")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, @PathVariable("id") String id) throws URISyntaxException, IOException, StorageException, InvalidKeyException {
		return ResponseEntity.ok(productService.uploadProductImage(file, redirectAttributes, id));
	}

	@GetMapping("/thumbnail/{id}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("id") String id) {

		final Resource file = productService.getProductImage(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
