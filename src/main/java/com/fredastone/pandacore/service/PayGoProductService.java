package com.fredastone.pandacore.service;

import java.math.BigInteger;
import java.util.List;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.entity.PayGoProduct;
import com.fredastone.pandacore.models.PayGoProductModel;
import com.fredastone.pandacore.models.StockProduct;

public interface PayGoProductService {	
	PayGoProduct addPayGoProduct(PayGoProductModel payGoProductModel);
	PayGoProduct getPayGoProduct(String serialToken);
	PayGoProduct updatePayGoProduct(PayGoProduct payGoProduct);
	PayGoProduct deletePayGoProduct(PayGoProduct payGoProduct);
	BigInteger getAvailablePayGoProduct();
	boolean isPayGoPayGoProductAvailable(String tokenSerialNumber);
	long countPayGoByStatus(PayGoProductStatus status, int leaseOfferid);
	List<StockProduct> getPayGoStockValues();
	
}
