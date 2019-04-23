package com.fredastone.pandacore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.Opex;
import com.fredastone.pandacore.repository.OpexRepository;
import com.fredastone.pandacore.service.OpexService;

@Service
public class OpexServiceImpl implements OpexService {

	private OpexRepository opexDao;
	
	@Autowired
	public OpexServiceImpl(OpexRepository opexDao) {
		// TODO Auto-generated constructor stub
		this.opexDao = opexDao;
	}
	
	@Override
	public Opex makeCapexRequest(Opex capex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Opex approvaCapexRequest(String capexId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Opex getCapexById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
