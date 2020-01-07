package com.fredastone.pandacore.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fredastone.pandacore.models.OpexModel;
import com.fredastone.pandacore.service.OpexService;

@RestController
@RequestMapping(path="v1/opex")
public class OpexController {
	
	private OpexService opexService;
	
	@Autowired
	public OpexController(OpexService opexService) {
		this.opexService = opexService;
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
	public ResponseEntity<?> addOpex(@Valid @RequestBody OpexModel opexModel){
		return ResponseEntity.ok(opexService.makeOpexRequest(opexModel));
	}
	
	@RequestMapping(path="get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOpexById(@Valid @PathVariable("id") String id){
		return ResponseEntity.ok(opexService.getOpexById(id));
	}
	
	@RequestMapping(path="get/employee/{id}",params = { "page","size" }, method = RequestMethod.GET)
	public ResponseEntity<?> getOpexByEmployeeId(@Valid @PathVariable("id") String employeeId,
			@RequestParam("size")int size,
			@RequestParam("page")int page){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdon").descending());
		return ResponseEntity.ok(opexService.getOpexByEmployee(employeeId, pageable));
	}
	

}
