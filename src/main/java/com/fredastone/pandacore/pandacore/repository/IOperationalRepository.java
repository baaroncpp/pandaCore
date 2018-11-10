package com.fredastone.pandacore.pandacore.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fredastone.pandacore.pandacore.entity.AccountFinancialInformation;

public interface IOperationalRepository {
	
	List<AccountFinancialInformation> getAccountFinancialInformation(@NotNull String meterNumber);

}
