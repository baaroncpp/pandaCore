package com.fredastone.pandacore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.EquipCategory;
import com.fredastone.pandacore.entity.Equipment;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.models.EquipmentModel;
import com.fredastone.pandacore.repository.EquipmentRepository;
import com.fredastone.pandacore.service.EquipmentService;
import com.fredastone.pandacore.util.ServiceUtils;

@Service
public class EquipmentServiceImpl implements EquipmentService{
	
	private EquipmentRepository equipmentDao;
	
	@Autowired
	public  EquipmentServiceImpl(EquipmentRepository equipmentDao) {
		// TODO Auto-generated constructor stub
		this.equipmentDao = equipmentDao;
	}

	@Override
	public Page<Equipment> getAllEquipment(int size, int page) {
		
		Page<Equipment> e = equipmentDao.findAll(PageRequest.of(page, size,Sort.by(Direction.DESC, "createdon")));
		
		return e;
	}

	@Override
	public Equipment addNewEquipment(EquipmentModel equip) {
		// TODO Auto-generated method stub
		Equipment e = new Equipment();
		e.setId(ServiceUtils.getUUID());
		e.setDateofmanufacture(equip.getDateofmanufacture());
		e.setDescription(equip.getDescription());
		e.setModel(equip.getModel());
		e.setName(equip.getName());
		e.setQuantity(equip.getQuantity());
		e.setSerial(equip.getSerial());
		e.setCategory(new EquipCategory());
		
		e.getCategory().setId(equip.getCategoryId());
		
		return equipmentDao.save(e);
	}

	//Update any updataeble part of equipment
	@Override
	public Equipment updateEquipment(Equipment equip) {
		// TODO log
		final Optional<Equipment> e = equipmentDao.findById(equip.getId());
		
		if(!e.isPresent())
		{
			throw new ItemNotFoundException(equip.getId());
		}
		
		e.get().setDateofmanufacture(equip.getDateofmanufacture());
		e.get().setDescription(equip.getDescription());
		e.get().setModel(equip.getModel());
		e.get().setName(equip.getName());
		e.get().setQuantity(equip.getQuantity());
		e.get().setSerial(equip.getSerial());
		//e.get().setCategory(new EquipCategory());
		
		e.get().getCategory().setId(equip.getCategory().getId());
		
		return equipmentDao.save(e.get());
	}

	@Override
	public Equipment findEquipmentBySerial(String serial) {
		// TODO log
		
		final Equipment e = equipmentDao.findEquipmentBySerial(serial);
		if(e == null){
			throw new ItemNotFoundException(serial);
		}
		
		return e;
	}

	@Override
	public Equipment findEquipmentById(String id) {
		// TODO log
		final Optional<Equipment> e = equipmentDao.findById(id);
		if(!e.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		
		return e.get();
	}

}
