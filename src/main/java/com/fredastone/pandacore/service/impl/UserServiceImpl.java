package com.fredastone.pandacore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.azure.AzureOperations;
import com.fredastone.pandacore.azure.IAzureOperations;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.UserService;
import com.fredastone.pandacore.util.ServiceUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("passwordEncoderBean")
	private BCryptPasswordEncoder passwordEncoder;

	
	private UserRepository userDao;
	private IAzureOperations azureOperations;
	
	@Autowired
	public UserServiceImpl(UserRepository userDao) {
		// TODO Auto-generated constructor stub
		this.userDao = userDao;
		
	}
	
	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		user.setId(ServiceUtils.getUUID());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userDao.save(user);
		user.setPassword(null);
		
		switch(user.getUsertype()) {
		case "AGENT":
			//Upload fileds required
			break;
		case "CUSTOMER":
			break;
		case "EMPLOYEE":
			break;
		default:
			break;
			
		}
		
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

	@Override
	public Page<User> getAllForApproval(int page, int size, Direction direction, String sortby,boolean approvalStatus) {
		
		final Pageable pageRequest = PageRequest.of(page, size,Sort.by(Direction.DESC,sortby));
		Page<User> allsorted = userDao.findAllByisapproved(pageRequest, approvalStatus);
	
		return allsorted;
	}

	

	@Override
	public Page<User> getUsers(int page, int size, Direction direction, String sortby) {
		
		final Pageable pageRequest = PageRequest.of(page, size,Sort.by(Direction.DESC,sortby));
		Page<User> allsorted = userDao.findAll(pageRequest);
	
		return allsorted;
	}

	@Override
	public Page<User> getAllForActive(int page, int size, Direction direction, String sortby, boolean isactive) {
		final Pageable pageRequest = PageRequest.of(page, size,Sort.by(Direction.DESC,sortby));
		Page<User> allsorted = userDao.findAllByisactive(pageRequest, isactive);
	
		return allsorted;
	}

	@Override
	public Page<User> getAllByType(int page, int size, Direction direction, String sortby, UserType type) {
		
		final Pageable pageRequest = PageRequest.of(page, size,Sort.by(Direction.DESC,sortby));
		Page<User> allsorted = userDao.findAllByusertype(pageRequest, type.name().toLowerCase());
	
		return allsorted;
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		return userDao.findLoginUser(username);
	}
}
