package com.fredastone.pandacore.service;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fredastone.pandacore.entity.Capex;
import com.fredastone.pandacore.models.CapexModel;

public interface CapexService {
	
	Capex makeCapexRequest(CapexModel capexModel);
	Capex approvaCapexRequest(String capexId);
	Capex getCapexById(String id);
	Page<Capex> getCapexByEmployee(String employeeId,Pageable pageable);
	Page<Capex> getCapexByDate(Date startDate, Date endDate, Pageable pageable);
	Capex updateCapex(String id, Capex capex);

}
