package com.fredastone.pandacore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort.Direction;
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

import com.fredastone.pandacore.entity.Installation;
import com.fredastone.pandacore.service.InstallationService;

import lombok.NoArgsConstructor;

@RestController
@RequestMapping("v1/installation")
@NoArgsConstructor
public class InstallationController {
	
	
	@Autowired
	private InstallationService installationService;
	
	
	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_AGENT,ROLE_SALE_AGENT"})
	@RequestMapping(path = "add", method = RequestMethod.POST)
	public ResponseEntity<?> addNewInstallation(@RequestBody Installation install) {	
		
		return ResponseEntity.ok(installationService.makeNewInstallation(install));

	}
	
	
	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_AGENT,ROLE_SALE_AGENT"})
	@RequestMapping(path = "update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateInstallation(@RequestBody Installation install) {
		
		return ResponseEntity.ok(installationService.updateInstallation(install));

	}

	
	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_AGENT,ROLE_SALE_AGENT"})
	  @RequestMapping(path="get",params = {"page","size","sortby","sortorder" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(
    		@Valid @RequestParam("sortorder") 
    		Direction sortOrder,
    		@Valid @RequestParam("sortby") 
    		String sortBy,
    		@Valid @RequestParam("page") 
    		int page,
    		@RequestParam("size") int size){
		
		return ResponseEntity.ok(installationService.getAllInstallation(page, size, sortBy, sortOrder));

	}
	
	
	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_AGENT,ROLE_SALE_AGENT"})
	@RequestMapping(path = "get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getInstallation(@PathVariable("id") String id) {
		
		return ResponseEntity.ok(installationService.getInstallationById(id));

	}
	

	@GetMapping("/media/{id}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("id") String customerId) {

		final Resource file = installationService.getProductImage(customerId);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	
	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_AGENT,ROLE_SALE_AGENT"})
	@PostMapping(value = "/thumbnail/{id}")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, @PathVariable("id") String id) {

		installationService.uploadProductImage(file, redirectAttributes, id);

		return ResponseEntity.ok().build();

	}

}
