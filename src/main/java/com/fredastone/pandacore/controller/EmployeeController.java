package com.fredastone.pandacore.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.service.EmployeeService;


@RestController
@RequestMapping("v1/employeemeta")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;	
	
	/*
	 * Adding an employee triggers an approval stage that may involve collecting documents from employee and adding them to payroll
	 */
	@RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<EmployeeMeta> addEmployeeMeta(@Valid @NotNull @RequestBody EmployeeMeta employee) {
		
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }
	
	@RequestMapping(path="update",method = RequestMethod.PUT)
	public ResponseEntity<?> updateEmployeeMeta(@Valid @NotNull @RequestBody EmployeeMeta employee){
		return ResponseEntity.ok(employeeService.updateEmployee(employee));
	}
	
    @RequestMapping(path="approve/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> approveEmployee(@PathVariable("id") String employeeId) {
    
        return null;
    }
	
    @RequestMapping(path="get",params = { "page","size" },method = RequestMethod.GET)
    public Page<?> getAllEmployees(@RequestParam("page") int page,@RequestParam("size") int size) {
        return employeeService.findAllEmployees( page, size);
    }
    
    @RequestMapping(path="get/{id}",method = RequestMethod.GET)
    public  ResponseEntity<?> getEmployeesById(@PathVariable("id") String id) {
    	
    	EmployeeMeta cm = employeeService.findEmployeeById(id);
    	
    	if(cm == null) {
			return ResponseEntity.noContent().build();
			
		}
    	
        return ResponseEntity.ok(cm);
    }
	
    @RequestMapping(path="get/terminated",params = {"direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllTerminatedEmployees(@RequestParam("direction") String direction,@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
   
    
	@PostMapping(value = "/uploads/{id}")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, @PathVariable("id") String id) {
		return ResponseEntity.ok(employeeService.uploadProfilePhoto(file, redirectAttributes, id));
	}

	@GetMapping("/media/{id}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("id") String id) {

		final Resource file = employeeService.getProfilePhoto(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}


}
