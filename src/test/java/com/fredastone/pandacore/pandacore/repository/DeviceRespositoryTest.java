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

import com.fredastone.pandacore.pandacore.PandacoreApplication;
import com.fredastone.pandacore.pandacore.entity.Devices;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeviceRespositoryTest {

	@Autowired
	private IDeviceRespository deviceRepo;
	@Test
	public void testGetAll() {
		
		List<Devices> dev = deviceRepo.getAllDevice();
		
		Assert.assertTrue(dev.size() >0);
		
	}

}
