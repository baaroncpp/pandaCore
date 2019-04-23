package com.fredastone.pandacore.service;

import org.springframework.data.domain.Page;

import com.fredastone.pandacore.entity.Equipment;
import com.fredastone.pandacore.models.EquipmentModel;

public interface EquipmentService {
	
	Page<Equipment> getAllEquipment(int size,int page);
	Equipment addNewEquipment(EquipmentModel equip);
	Equipment updateEquipment(Equipment equip);
	Equipment findEquipmentBySerial(String serial);
	Equipment findEquipmentById(String id);
	

}
