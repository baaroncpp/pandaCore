package com.fredastone.pandacore.service;

import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.models.LeaseSale;

public interface SaleService {
	
	Sale recoredNewDirectSale(Sale sale);
	LeaseSale recoredNewLeaseSale(int leaseId,String agentid,String customerid,float cord_lat,float cord_long,String scanneddeviceid);
	
	Sale completeSale(String saleId);
	Sale rollbackSale(String saleId);
	
}
