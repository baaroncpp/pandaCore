package com.fredastone.pandacore.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fredastone.pandacore.entity.Approval;
import com.fredastone.pandacore.entity.Config;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.repository.ApproverRepository;
import com.fredastone.pandacore.repository.ConfigRepository;
import com.fredastone.pandacore.repository.EmployeeRepository;
import com.fredastone.pandacore.repository.VEmployeeApprovalRepository;
import com.fredastone.pandacore.service.impl.EmployeeServiceImpl;
import com.fredastone.pandasolar.token.CommandNames;
import com.fredastone.pandasolar.token.TokenOperation;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class EmployeeServiceTest {
	
	/*
	 * private EmployeeService employeeService; private EmployeeRepository
	 * employeeDao; private ApproverRepository approvalDao; private ConfigRepository
	 * configDao; private VEmployeeApprovalRepository vapprovalDao;
	 * 
	 * @Before public void before() {
	 * 
	 * employeeDao = Mockito.mock(EmployeeRepository.class); approvalDao =
	 * Mockito.mock(ApproverRepository.class); configDao =
	 * Mockito.mock(ConfigRepository.class); vapprovalDao =
	 * Mockito.mock(VEmployeeApprovalRepository.class);
	 * 
	 * 
	 * Config ecConfig = new Config(); ecConfig.setName("EMPLOYEEAPPROVALCOUNT");
	 * ecConfig.setValue("2");
	 * 
	 * Config erconfig = new Config(); erconfig.setName("EMPLOYEEAPPROVALROLES");
	 * erconfig.setValue("ADMIN,MANAGER");
	 * 
	 * 
	 * when(configDao.findByName("EMPLOYEEAPPROVALCOUNT")).thenReturn(Optional.of(
	 * ecConfig));
	 * when(configDao.findByName("EMPLOYEEAPPROVALROLES")).thenReturn(Optional.of(
	 * erconfig));
	 * 
	 * 
	 * //this.employeeService = new EmployeeServiceImpl(employeeDao, approvalDao,
	 * configDao, vapprovalDao);
	 * 
	 * }
	 * 
	 * @Test public void testThatCanCreateNewEmployee() {
	 * 
	 * // EmployeeMeta ep = new EmployeeMeta(); //
	 * ep.setCompanyemail("starnapho@gmail.com"); // ep.setCreateon(new Date()); //
	 * ep.setDateofbirth(new Date()); // ep.setFirstname("Naphlin"); //
	 * ep.setId("48848488484884848"); // ep.setIsapproved(Boolean.FALSE); //
	 * ep.setIsterminated(Boolean.FALSE); // ep.setLastname("Akena"); //
	 * ep.setMiddlename(""); // ep.setMobile("256777110054"); //
	 * ep.setPersonalemail("starnapho@gmail.com"); // ep.setPhone("256777110054");
	 * // ep.setProfilepath("http://"); // //
	 * when(employeeDao.save(ep)).thenReturn(ep); // when(approvalDao.save(new
	 * Approval())).then(Answers.RETURNS_SELF); // // // EmployeeMeta resp =
	 * this.employeeService.createEmployee(ep); // Assert.assertNotNull(resp); //
	 * Assert.assertEquals(resp, ep);
	 * 
	 * TokenOperation op = new TokenOperation();
	 * System.out.println(op.generateGeneralPurposeToken("168005009023004",
	 * CommandNames.DEMOLISH, 1));
	 * 
	 * }
	 */	
}
