package com.fredastone.pandacore.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

import com.fredastone.pandacore.constants.CustomerUploadType;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.models.CustomerModel;
import com.fredastone.pandacore.service.CustomerService;
import com.fredastone.security.JwtTokenUtil;
import com.microsoft.azure.storage.StorageException;

@RestController
@RequestMapping("v1/customermeta")
public class CustomerController {

	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	private CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
		
	}
	
	@RequestMapping(path="add/mobile",method = RequestMethod.POST)
	public ResponseEntity<?> addMobileCustomer(@Valid @NotNull @RequestBody CustomerModel customerModel) throws InvalidKeyException, MalformedURLException{
		return ResponseEntity.ok(customerService.addMobileCustomer(customerModel));
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addCustomerMeta(@Valid @NotNull @RequestBody CustomerMeta customerMeta) {
		
        return ResponseEntity.ok(customerService.addCustomerMeta(customerMeta));
    }
	
	@RequestMapping(path="get/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerMeta(@PathVariable("id") String id) {
		
		CustomerMeta cm = customerService.getCustomerMeta(id);
		if(cm == null) {
			return ResponseEntity.noContent().build();
		}
        return ResponseEntity.ok(cm);
    }
	
    @PostMapping(value = "/uploads/{id}")
	public ResponseEntity<?> handleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, @PathVariable("id") String id, @RequestParam("uploadType") CustomerUploadType uploadType) throws InvalidKeyException, MalformedURLException, URISyntaxException, IOException, StorageException {

    	String userid = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
    	
		return ResponseEntity.ok(customerService.uploadMetaInfo(userid, file, redirectAttributes, id, uploadType));

	}
    
    @RequestMapping(path="get/all", params = {"page","size","sortby","sortorder" }, method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomerMeta(@Valid @RequestParam("sortorder") Direction sortorder,
									    		@Valid @RequestParam("sortby") String sortby,
									    		@Valid @RequestParam("page") int page,
									    		@RequestParam("size") int size) throws InvalidKeyException, MalformedURLException{
    	
    	return ResponseEntity.ok(customerService.getAllCustomerMeta(page, size, sortby, sortorder));
    }

//	@GetMapping("/media/{id}")
//	@ResponseBody
//	public ResponseEntity<Resource> serveFile(@PathVariable("id") String id,@RequestParam("uploadtype") CustomerUploadType uploadType) {
//
//		final Resource file = customerService.getUploadedMetaInfo(id,uploadType);
//
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//				.body(file);
//	}
}
