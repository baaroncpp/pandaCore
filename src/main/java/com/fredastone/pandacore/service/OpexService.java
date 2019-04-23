package com.fredastone.pandacore.service;

import com.fredastone.pandacore.entity.Opex;

public interface OpexService {
	
	Opex makeCapexRequest(Opex capex);
	Opex approvaCapexRequest(String capexId);
	Opex getCapexById(String id);


}
