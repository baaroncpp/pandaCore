package com.fredastone.pandacore.service;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.User;

public interface UserService {

	User addUser(User user) throws InvalidKeyException, MalformedURLException;
	User updateUser(User user);
	User getUserByEmail(String email);
	User getUserById(String id);
	Optional<User> getUserByUsername(String username);
	
	Page<User> getUsers(int page, int size, Direction direction, String sortby);
	Page<User> getAllForApproval(int page, int size, Direction direction, String sortby,boolean approvalStatus);
	Page<User> getAllForActive(int page, int size, Direction direction, String sortby,boolean approvalStatus);
	Page<User> getAllByType(int page, int size, Direction direction, String sortby,UserType type);
	
	
	User changePassword(String userId,String oldPassword,String newPassword,String token);
	void forgotPassword(String userId);
	void updateLastLogon(String userId,String lastLogon);
}
