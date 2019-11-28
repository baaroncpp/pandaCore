package com.fredastone.pandacore.service;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.models.FileResponse;

public interface EmployeeService {
	
	EmployeeMeta addEmployee(EmployeeMeta employeemeta);
	EmployeeMeta updateEmployee(EmployeeMeta employeemeta);
	public Page<EmployeeMeta> findAllEmployees(int page,int size);
	public EmployeeMeta findEmployeeById(String id);
	public Optional<EmployeeMeta> findEmployeeByName(String name);
	public Optional<EmployeeMeta> findEmployeeByMobile(String mobile);
	public EmployeeMeta findEmployeeByEmail(String email);
	FileResponse uploadProfilePhoto(MultipartFile file,  RedirectAttributes redirectAttributes,String employeeId);
	Resource getProfilePhoto(String employeeId);

}
