package com.fredastone.pandacore.service;

import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.entity.EmployeeMeta;

public interface EmployeeService {
	
	EmployeeMeta addEmployee(EmployeeMeta employeemeta);
	public Page<EmployeeMeta> findAllEmployees(int page,int size);
	public Optional<EmployeeMeta> findEmployeeById(String id);
	public Optional<EmployeeMeta> findEmployeeByName(String name);
	public Optional<EmployeeMeta> findEmployeeByMobile(String mobile);
	public Optional<EmployeeMeta> findEmployeeByEmail(String email);
	void uploadProfilePhoto(MultipartFile file,  RedirectAttributes redirectAttributes,String employeeId);
	Resource getProfilePhoto(String employeeId);
	

}
