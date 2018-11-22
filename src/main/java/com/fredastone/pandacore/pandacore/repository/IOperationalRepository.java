package com.fredastone.pandacore.pandacore.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fredastone.pandacore.pandacore.entity.AccountFinancialInformation;
import com.fredastone.pandacore.pandacore.entity.FeesCollected;


public interface IOperationalRepository {
	
	List<AccountFinancialInformation> getAccountFinancialInformation(@NotNull String meterNumber);
	String getPaymentToken(float amount);

	int createNewCollectedFee(FeesCollected feeCollected);
}
