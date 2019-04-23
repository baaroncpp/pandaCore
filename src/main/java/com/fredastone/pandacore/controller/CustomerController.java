package com.fredastone.pandacore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.constants.AgentUploadType;
import com.fredastone.pandacore.constants.CustomerUploadType;
import com.fredastone.pandacore.service.CustomerService;

@RestController
@RequestMapping("v1/customer")
public class CustomerController {

	
	private CustomerService customerService;
	
	@Autowired
	public CustomerController() {
		// TODO Auto-generated constructor stub
	}
	
    @PostMapping(value = "/uploads/{id}")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, @PathVariable("id") String id,@RequestParam("uploadType") CustomerUploadType uploadType) {

		customerService.uploadMetaInfo(file, redirectAttributes, id, uploadType);

		return ResponseEntity.ok().build();

	}

	@GetMapping("/media/{id}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("id") String id,@RequestParam("uploadtype") CustomerUploadType uploadType) {

		final Resource file = customerService.getUploadedMetaInfo(id,uploadType);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
