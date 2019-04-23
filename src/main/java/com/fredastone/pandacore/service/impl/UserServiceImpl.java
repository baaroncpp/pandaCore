package com.fredastone.pandacore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.UserService;
import com.fredastone.pandacore.util.ServiceUtils;

@Service
public class UserServiceImpl implements UserService {

	
	private UserRepository userDao;
	
	@Autowired
	public UserServiceImpl(UserRepository userDao) {
		// TODO Auto-generated constructor stub
		this.userDao = userDao;
	}
	
	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		user.setId(ServiceUtils.getUUID());
		userDao.save(user);
		user.setPassword(null);
		return user;
	}

	@Override
	public User updateUser(User u) {
		// TODO Auto-generated method stub
		Optional<User> user = userDao.findById(u.getId());
		
		if(!user.isPresent()) {
			throw new ItemNotFoundException(u.getId());
		}
		
		
		user.get().setCompanyemail(u.getCompanyemail());
		user.get().setDateofbirth(u.getDateofbirth());
		user.get().setEmail(u.getEmail());
		user.get().setFirstname(u.getFirstname());
		user.get().setIsactive(u.isIsactive());
		user.get().setLastname(u.getLastname());
		user.get().setMiddlename(u.getMiddlename());
		user.get().setPrimaryphone(u.getPrimaryphone());
		
		userDao.save(user.get());
		user.get().setPassword(null);
		
		return user.get();
	}

	@Override
	public User getUserByEmail(String email) {
		Optional<User> user = userDao.findByEmail(email);
		
		if(!user.isPresent())
			throw new ItemNotFoundException(email);
		
		user.get().setPassword(null);
		return user.get();
	}

	@Override
	public User getUserById(String id) {
		Optional<User> user = userDao.findById(id);
		
		if(!user.isPresent())
			throw new ItemNotFoundException(id);
		
		user.get().setPassword(null);
		return user.get();
	}

	@Override
	public List<User> getUsers(int page, int size, String direction, boolean state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User changePassword(String userId, String oldPassword, String newPassword, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forgotPassword(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLastLogon(String userId, String lastLogon) {
		// TODO Auto-generated method stub
		
	}
	
}
