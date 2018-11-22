package com.fredastone.pandacore.pandacore.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fredastone.commons.logging.InterfaceLog;
import com.fredastone.commons.logging.bean.InterfaceLogParamsMandatory;
import com.fredastone.commons.logging.bean.InterfaceLogParamsOptional;
import com.fredastone.pandacore.pandacore.PandacoreApplication;
import com.fredastone.pandacore.pandacore.entity.Devices;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeviceRespositoryTest {

	@Autowired
	private IDeviceRespository deviceRepo;
	
	@Autowired
	private InterfaceLog interfaceLog;
	
	@Test
	public void testGetAll() {
		
		List<Devices> dev = deviceRepo.getAllDevice();
		
		Assert.assertTrue(dev.size() >0);
		
		InterfaceLogParamsMandatory mandatoryParam = new 
				InterfaceLogParamsMandatory("test", "interfaceType", "interfaceName", "sourceDevice", "destDevice");
		
		InterfaceLogParamsOptional optionalParam = new InterfaceLogParamsOptional
				("transactionID", "msisdn", "accountId", "extendedInfo", "traceUniqueID");
		
		interfaceLog.info(mandatoryParam, optionalParam);
		
	}

}
