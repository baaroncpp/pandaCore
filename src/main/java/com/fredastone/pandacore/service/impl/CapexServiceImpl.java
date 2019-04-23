package com.fredastone.pandacore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.Capex;
import com.fredastone.pandacore.repository.CapexRepository;
import com.fredastone.pandacore.service.CapexService;

@Service
public class CapexServiceImpl implements CapexService {

	private CapexRepository capexDao;
	
	@Autowired
	public CapexServiceImpl(CapexRepository capexDao) {
		// TODO Auto-generated constructor stub
		this.capexDao = capexDao;
	}
	
	@Override
	public Capex makeCapexRequest(Capex capex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Capex approvaCapexRequest(String capexId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Capex getCapexById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
