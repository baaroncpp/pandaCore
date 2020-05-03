package com.fredastone.pandacore.service;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fredastone.pandacore.entity.Opex;
import com.fredastone.pandacore.models.OpexModel;

public interface OpexService {
	
	Opex makeOpexRequest(OpexModel opexModel);
	Opex getOpexById(String id);
	Page<Opex> getOpexByEmployee(String employeeId, Pageable pageable);
	Page<Opex> getOpexByDate(Date from, Date to, Pageable pageable);
	Opex updateOpex(String id, Opex opex);
}
