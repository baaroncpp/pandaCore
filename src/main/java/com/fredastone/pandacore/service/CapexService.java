package com.fredastone.pandacore.service;

import com.fredastone.pandacore.entity.Capex;

public interface CapexService {
	
	
	Capex makeCapexRequest(Capex capex);
	Capex approvaCapexRequest(String capexId);
	Capex getCapexById(String id);

}
