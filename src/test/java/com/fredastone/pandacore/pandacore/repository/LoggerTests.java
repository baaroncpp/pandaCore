package com.fredastone.pandacore.pandacore.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fredastone.commons.logging.DebugLog;
import com.fredastone.commons.logging.InterfaceLog;
import com.fredastone.commons.logging.RuntimeLog;
import com.fredastone.commons.logging.bean.InterfaceLogParamsMandatory;
import com.fredastone.commons.logging.bean.InterfaceLogParamsOptional;
import com.fredastone.commons.logging.impl.SecurityLog;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoggerTests {

	/*
	 * 
	 * 
	 */
	@Autowired
	private InterfaceLog interfaceLog;
	
	@Autowired 
	private DebugLog debugLog;
	
	@Autowired
	private RuntimeLog runtimeLog;
	
	@Autowired
	private SecurityLog securityLog;
	
	@Test
	public void testInterfaceLog() {
		
	
		InterfaceLogParamsMandatory mandatoryParam = new 
				InterfaceLogParamsMandatory("test", "interfaceType", "interfaceName", "sourceDevice", "destDevice");
		
		InterfaceLogParamsOptional optionalParam = new InterfaceLogParamsOptional
				("transactionID", "msisdn", "accountId", "extendedInfo", "traceUniqueI3");
		
		interfaceLog.info(mandatoryParam, optionalParam);	
	}
	
	@Test
	public void testDebugLog() {
		
	
		debugLog.debug("type","msisdn"," transactionId","traceUniqueID",
				"classFileName","classFileLine","stackTrace");
				
	}
	
	
	@Test
	public void testRuntimeLog() {
		
		runtimeLog.info( "operation", "traceuniqueid", "message");
	}
	
	@Test
	public void testSecurityLog()
	{
		securityLog.error("userName", "resultInfo", "staticInfo", "externalInfo");
	}

}
