package com.fredastone.pandacore.repository;

import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.models.BuyToken;

public interface BuyTokenRepository {

	Token commitPayment(BuyToken paymentRequest);
}
