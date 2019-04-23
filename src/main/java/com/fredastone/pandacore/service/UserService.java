package com.fredastone.pandacore.service;

import java.util.List;

import com.fredastone.pandacore.entity.User;

public interface UserService {

	User addUser(User user);
	User updateUser(User user);
	User getUserByEmail(String email);
	User getUserById(String id);
	
	List<User> getUsers(int page, int size, String direction, boolean state);
	User changePassword(String userId,String oldPassword,String newPassword,String token);
	void forgotPassword(String userId);
	void updateLastLogon(String userId,String lastLogon);
}
