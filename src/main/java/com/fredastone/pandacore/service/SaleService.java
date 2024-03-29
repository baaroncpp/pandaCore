package com.fredastone.pandacore.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.SaleRollBackRefund;
import com.fredastone.pandacore.entity.SaleRollback;
import com.fredastone.pandacore.entity.VLeaseSaleDetails;
import com.fredastone.pandacore.models.LeaseSale;
import com.fredastone.pandacore.models.SaleModel;

public interface SaleService {
	
	Sale noPaygoSale(Sale sale);
	Sale recoredNewDirectSale(Sale sale);
	LeaseSale recoredNewLeaseSale(int leaseId,String agentid,String customerid,float cord_lat,float cord_long,String scanneddeviceid);
	
	Sale completeSale(String saleId, String approverId);
	SaleRollback rollbackSale(String saleId, String description);
	Page<Sale> getVerifiedLeaseSale(String agentId,int page,int count,String sortBy,Direction sortOrder);
	Page<Sale> getUnverifiedleaseSale(String agentId,int page,int count,String sortBy,Direction orderBy);
	
	Page<VLeaseSaleDetails> getAllLeaseSaleByReviewStatus(boolean reviewStatus,int page,int count,String sortby,Direction orderby);
	VLeaseSaleDetails getLeaseSaleDetail(String saleid);
	Page<VLeaseSaleDetails> getAllLeaseSaleDetail(int page,int count,String sortby,Direction orderby);
		
	List<SaleModel> getAllSales(int page,int count, String sortby,Direction sortOrder);
	List<SaleModel> getAllSalesByAgentId(String agentId,int page,int count,String sortBy,Direction orderBy);
	
	List<SaleModel> mobileUserGetSales(String userId,int page,int count,String sortBy,Direction orderBy);
	
	Map<String, Integer> getAgentSaleSums(String agentId);
	Map<String, Integer> getCustomerSaleSums(String customerId);
	
	SaleRollBackRefund makeSaleRollBackRefund(String id);
	
	Iterable<SaleRollBackRefund> getAllSaleRefunds();
}
