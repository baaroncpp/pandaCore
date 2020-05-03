package com.fredastone.pandacore.service.impl;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.entity.Opex;
import com.fredastone.pandacore.entity.OpexType;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.models.OpexModel;
import com.fredastone.pandacore.repository.EmployeeRepository;
import com.fredastone.pandacore.repository.OpexRepository;
import com.fredastone.pandacore.repository.OpexTypeRepository;
import com.fredastone.pandacore.service.OpexService;
import com.fredastone.pandacore.util.ServiceUtils;

@Service
public class OpexServiceImpl implements OpexService {

	private OpexRepository opexDao;
	private EmployeeRepository employeeRepository;
	private OpexTypeRepository opexTypeRepository;
	
	@Autowired
	public OpexServiceImpl(OpexRepository opexDao, EmployeeRepository employeeRepository, OpexTypeRepository opexTypeRepository) {
		this.opexDao = opexDao;
		this.employeeRepository = employeeRepository;
		this.opexTypeRepository = opexTypeRepository;
	}
	
	@Override
	public Opex makeOpexRequest(OpexModel opexModel) {
		
		Optional<EmployeeMeta> empMeta = employeeRepository.findById(opexModel.getEmployeeId());
		
		Optional<OpexType> opexType = opexTypeRepository.findById(opexModel.getOpexTypeid());
		
		if(!empMeta.isPresent()) {
			throw new RuntimeException("Eployee with ID: "+opexModel.getEmployeeId()+" does not exist");
		}
		
		Opex opex = new Opex();
		
		opex.setId(ServiceUtils.getUUID());
		opex.setAmount(opexModel.getAmount());
		opex.setApprovedon(null);
		opex.setCreatedon(new Date());
		opex.setTEmployees(empMeta.get());
		opex.setTOpexType(opexType.get());
		opex.setIsapproved(Boolean.FALSE);
		
		opexDao.save(opex);
		
		return null;
	}

	@Override
	public Opex getOpexById(String id) {
		
		Optional<Opex> opex = opexDao.findById(id);
		
		if(!opex.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		
		return opex.get();
	}

	@Override
	public Page<Opex> getOpexByEmployee(String employeeId, Pageable pageable) {
		
		Optional<EmployeeMeta> empMeta = employeeRepository.findById(employeeId);
		
		if(!empMeta.isPresent()) {
			throw new RuntimeException("Eployee with ID: "+employeeId+" does not exist");
		}
		
		Page<Opex> opex = opexDao.findAllByEmployee(empMeta.get(), pageable);
		
		if(opex.isEmpty()) {
			throw new RuntimeException("Null results"); 
		}
		
		return opex;
	}

	@Override
	public Opex updateOpex(String id, Opex opex) {
		
		Optional<Opex> opexDb = opexDao.findById(id);
		
		if(!opexDb.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		
		opex.setId(id);
		opexDao.save(opex);
		
		return opex;
	}

	@Override
	public Page<Opex> getOpexByDate(Date from, Date to, Pageable pageable) {
		
		Page<Opex> opex = opexDao.findAllByCreatedonBetween(from, to, pageable);
		
		if(opex.isEmpty()) {
			throw new RuntimeException("Null Results");
		}
		return opex;
	}
	
}
