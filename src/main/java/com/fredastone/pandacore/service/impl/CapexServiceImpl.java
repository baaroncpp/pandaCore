package com.fredastone.pandacore.service.impl;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fredastone.pandacore.entity.Capex;
import com.fredastone.pandacore.entity.CapexType;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.models.CapexModel;
import com.fredastone.pandacore.repository.CapexRepository;
import com.fredastone.pandacore.repository.CapexTypeRepository;
import com.fredastone.pandacore.repository.EmployeeRepository;
import com.fredastone.pandacore.service.CapexService;
import com.fredastone.pandacore.util.ServiceUtils;

@Service
public class CapexServiceImpl implements CapexService {

	private CapexRepository capexDao;
	private EmployeeRepository employeeReposioty;
	private CapexTypeRepository capexTypeRepository;
	
	@Autowired
	public CapexServiceImpl(CapexRepository capexDao, EmployeeRepository employeeReposioty, CapexTypeRepository capexTypeRepository) {
		// TODO Auto-generated constructor stub
		this.capexDao = capexDao;
		this.employeeReposioty = employeeReposioty;
		this.capexTypeRepository = capexTypeRepository;
	}
	
	@Override
	@Transactional
	public Capex makeCapexRequest(CapexModel capexModel) {
		
		Optional<EmployeeMeta> empMeta = employeeReposioty.findById(capexModel.getEmployeeid());
		Optional<CapexType> capexType = capexTypeRepository.findById(capexModel.getCapexTypeid());
		
		if(!empMeta.isPresent()) {
			throw new RuntimeException("Employee of ID: "+capexModel.getEmployeeid()+" does not exist");
		}
		
		Capex capex = new Capex();
		
		capex.setId(ServiceUtils.getUUID());
		capex.setAmount(capexModel.getAmount());
		capex.setDescription(capexModel.getDescription());
		capex.setCreatedon(new Date());
		capex.setTEmployees(empMeta.get());
		capex.setApprovedon(null);
		capex.setTCapexType(capexType.get());
		capex.setIsapproved(Boolean.FALSE);
		
		capexDao.save(capex);
		
		return capex;
	}

	@Override
	public Capex approvaCapexRequest(String capexId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Capex getCapexById(String id) {
		
		Optional<Capex> capex = capexDao.findById(id);
		
		if(!capex.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		
		return capex.get();
	}
	
	@Transactional
	@Override
	public Page<Capex> getCapexByEmployee(String employeeId, Pageable pageable){
		
		Optional<EmployeeMeta> empMeta = employeeReposioty.findById(employeeId);
		
		if(!empMeta.isPresent()) {
			throw new ItemNotFoundException(employeeId);
		}
		
		Page<Capex> capex = capexDao.findAllByEmployeeId(empMeta.get(), pageable);
		
		if(capex.isEmpty()) {
			throw new RuntimeException("Null Results");
		}
		
		return capex;
	}

	@Override
	public Page<Capex> getCapexByDate(Date startDate, Date endDate, Pageable pageable) {
		
		Page<Capex> capex = capexDao.findAllByCreatedonBetween(startDate, endDate, pageable);
		
		if(capex.isEmpty()) {
			throw new RuntimeException("Null Results");
		}
		
		return capex;
	}

	@Override
	public Capex updateCapex(String id, Capex capex) {
		
		Optional<Capex> capexObj = capexDao.findById(id);
		
		if(!capexObj.isPresent()) {
			throw new ItemNotFoundException(id);
		}
		
		capex.setId(id);
		capexDao.save(capex);
		
		return capex;
	}

}
