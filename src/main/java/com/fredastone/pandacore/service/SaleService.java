package com.fredastone.pandacore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.models.LeaseSale;

public interface SaleService {
	
	Sale recoredNewDirectSale(Sale sale);
	LeaseSale recoredNewLeaseSale(int leaseId,String agentid,String customerid,float cord_lat,float cord_long,String scanneddeviceid);
	
	Sale completeSale(String saleId);
	Sale rollbackSale(String saleId);
	
	
	Page<Sale> getAllSales(int page,int count, String sortby,Direction sortOrder);
}
