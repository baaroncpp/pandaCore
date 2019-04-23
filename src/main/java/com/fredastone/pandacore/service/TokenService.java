package com.fredastone.pandacore.service;

import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.entity.VCustomerFinanceInfo;
import com.fredastone.pandacore.models.BuyToken;

public interface TokenService {
	
	Token buyToken(BuyToken token);
	VCustomerFinanceInfo getBalanceForTokenPayment(String deviceserial);
	Token createToken(String customerId);
	Token getToken(String tokenId);
	Token getTokenByPaymentReference(String paymentReference);
	Token invalidateToken(String token);
	Token resendToken(String paymentReference);

}
