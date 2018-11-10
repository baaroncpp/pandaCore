package com.fredastone.pandacore.pandacore.repository;

import com.fredastone.pandacore.pandacore.entity.UserAccount;


public interface IAccountsRepository {
	
	UserAccount getTransAccountByMeterNo(String meterNumber);
	int checkValidAccountExists(String meterNumber);

}
