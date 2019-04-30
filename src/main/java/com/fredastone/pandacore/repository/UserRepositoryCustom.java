package com.fredastone.pandacore.repository;

import com.fredastone.pandacore.entity.LoginUser;

public interface UserRepositoryCustom {
	
	
	LoginUser getLoginUser(String username);
	
}
