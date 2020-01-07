package com.fredastone.pandacore.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.fredastone.pandacore.entity.Capex;
import com.fredastone.pandacore.models.CapexModel;
import com.fredastone.pandacore.service.CapexService;

@RestController
@RequestMapping(path="v1/capex")
public class CapexController {
	
	private CapexService capexService;
	
	@Autowired
	public CapexController(CapexService capexService) {
		this.capexService = capexService;
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
	public ResponseEntity<?> addCapex(@Valid @RequestBody CapexModel capexModel){
		return ResponseEntity.ok(capexService.makeCapexRequest(capexModel));		
	}
	
	@RequestMapping(path="get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCapexById(@Valid @PathVariable("id") String Id){
		return ResponseEntity.ok(capexService.getCapexById(Id));		
	}
	
	@RequestMapping(path="get/employee/{id}",params = { "page","size" }, method = RequestMethod.GET)
	public ResponseEntity<?> getCapexByEmployeeId(@Valid @PathVariable("id") String employeeId,
			@RequestParam("size")int size,
			@RequestParam("page")int page){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdon").descending());
		return ResponseEntity.ok(capexService.getCapexByEmployee(employeeId, pageable));
	}
	
	//PENDING
	@RequestMapping(path="get/bydate",params = {"from","to","page","size"}, method = RequestMethod.GET)
	public ResponseEntity<?> getCapexByDate(@Valid @RequestParam("from")String fromDate, 
			@RequestParam("to")String toDate,
			@RequestParam("size")int size,
			@RequestParam("page")int page) throws ParseException{
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdon").descending());
		return ResponseEntity.ok(capexService.getCapexByDate(stringToDate(fromDate), stringToDate(toDate), pageable));
	}
	
	@RequestMapping(path = "update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCapex(@Valid @PathVariable("Id") String Id, @RequestBody Capex capex){
		return ResponseEntity.ok(capexService.updateCapex(Id, capex));
	}
	
	public Date stringToDate(String sDate) throws ParseException {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse(sDate));
		return new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
	}
	
}