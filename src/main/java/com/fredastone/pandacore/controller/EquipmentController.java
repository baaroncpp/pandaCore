package com.fredastone.pandacore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
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

import com.fredastone.pandacore.entity.Equipment;
import com.fredastone.pandacore.models.EquipmentModel;
import com.fredastone.pandacore.service.EquipmentService;
import com.fredastone.pandacore.service.StorageService;
import com.microsoft.applicationinsights.core.dependencies.apachecommons.io.FilenameUtils;

@RestController
@RequestMapping("v1/equipment")
public class EquipmentController {

	private EquipmentService equipmentService;
	private StorageService storageService;

	@Value("${equipmentphotosfolder}")
	private String photosFolder;

	@Autowired
	public EquipmentController(EquipmentService equipmentService, StorageService storageService) {
		// TODO Auto-generated constructor stub
		this.equipmentService = equipmentService;
		this.storageService = storageService;
	}

	@Secured({ "ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE" })
	@RequestMapping(path = "add", method = RequestMethod.POST)
	public ResponseEntity<?> addNewEquipment(@RequestBody EquipmentModel equipment) {

		Equipment e = equipmentService.addNewEquipment(equipment);

		return ResponseEntity.ok(e);
	}

	@Secured({ "ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE" })
	@RequestMapping(path = "update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEquipment(@RequestBody Equipment equipment) {

		Equipment e = equipmentService.updateEquipment(equipment);

		return ResponseEntity.ok(e);
	}

	@RequestMapping(path = "get", params = { "page", "size" }, method = RequestMethod.GET)
	public Page<?> getAllEquipment(@RequestParam("page") int page, @RequestParam("size") int size) {

		return equipmentService.getAllEquipment(size, page);
	}

	@RequestMapping(path = "get/serial/{serial}", method = RequestMethod.GET)
	public ResponseEntity<?> getEquipmentBySerial(@PathVariable("serial") String serial) {

		return ResponseEntity.ok(equipmentService.findEquipmentBySerial(serial));
	}

	@RequestMapping(path = "get/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getEquipmentById(@PathVariable("id") String id) {

		return ResponseEntity.ok(equipmentService.findEquipmentById(id));
	}

	@Secured({ "ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE" })
	@PostMapping(value = "/photo/{id}")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, @PathVariable("id") String id) {

		Equipment eq = equipmentService.findEquipmentById(id);

		if (eq == null) {
			return ResponseEntity.notFound().build();
		}

		storageService.store(file,
				String.format("%s/%s.%s", photosFolder, id, FilenameUtils.getExtension(file.getOriginalFilename())));

		eq.setEquipmentPhoto(String.format("%s.%s", id, FilenameUtils.getExtension(file.getOriginalFilename())));

		equipmentService.updateEquipment(eq);

		return ResponseEntity.ok().build();

	}

	@GetMapping("/photo/{id}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("id") String id) {

		Equipment eq = equipmentService.findEquipmentById(id);

		if (eq == null) {
			return ResponseEntity.notFound().build();
		}
		Resource file = storageService.loadAsResource(String.format("%s/%s", photosFolder, eq.getEquipmentPhoto()));

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
