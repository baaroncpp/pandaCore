package com.fredastone.pandacore.service;

import java.math.BigInteger;
import com.fredastone.pandacore.entity.PayGoProduct;
import com.fredastone.pandacore.models.PayGoProductModel;

public interface PayGoProductService {	
	PayGoProduct addPayGoProduct(PayGoProductModel payGoProductModel);
	PayGoProduct getPayGoProduct(String serialToken);
	PayGoProduct updatePayGoProduct(PayGoProduct payGoProduct);
	PayGoProduct deletePayGoProduct(PayGoProduct payGoProduct);
	BigInteger getAvailablePayGoProduct();
	boolean isPayGoPayGoProductAvailable(String tokenSerialNumber);
}
