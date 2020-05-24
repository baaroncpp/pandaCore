package com.fredastone.pandacore.service.impl;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.fredastone.pandacore.azure.IAzureOperations;
import com.fredastone.pandacore.constants.Operations;
import com.fredastone.pandacore.constants.OperationsResults;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.PasswordResetToken;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.exception.ValueTakenException;
import com.fredastone.pandacore.mail.EmailService;
import com.fredastone.pandacore.models.OperationsStatusModel;
import com.fredastone.pandacore.models.PasswordResetModel;
import com.fredastone.pandacore.repository.PasswordResetTokenRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.service.UserService;
import com.fredastone.pandacore.util.ServiceUtils;
import com.fredastone.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	@Qualifier("passwordEncoderBean")
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private UserRepository userDao;
	private IAzureOperations azureOperations;
	private EmailService emailService;
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userDao,
			IAzureOperations azureOperations, 
			EmailService emailService, 
			PasswordResetTokenRepository passwordResetTokenRepository) {
		
		// TODO Auto-generated constructor stub
		this.userDao = userDao;
		this.azureOperations = azureOperations;
		this.emailService = emailService;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		
	}
	
	@Transactional
	@Override
	public User addUser(User user) throws InvalidKeyException, MalformedURLException {
		// TODO Auto-generated method stub
		user.setId(ServiceUtils.getUUID());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setIsactive(Boolean.FALSE);
		
		if(user.getPrimaryphone() != null && !user.getPrimaryphone().isEmpty())
		{
			user.setPrimaryphone(ServiceUtils.reformatUGPhoneNumbers(user.getPrimaryphone()));
		}
		
		if (validateUserDetails(user)) {
			userDao.save(user);
		} 
		
		user.setPassword(null);
		
		final String profilePath = azureOperations.uploadProfile(user.getId());
		final String idCopyPath = azureOperations.uploadIdCopy(user.getId());
		
		user.setProfilepath(profilePath);
		user.setIdcopypath(idCopyPath);
		
		switch(user.getUsertype()) {
		case "AGENT":
			//Upload fileds required
			final String contractPath = azureOperations.uploadAgentContract(user.getId());
			final String coiPath = azureOperations.uploadAgentCertIncorp(user.getId());
			
			user.setContractpath(contractPath);
			user.setCoipath(coiPath);
			break;
		case "CUSTOMER":
			
			final String consentFormPath = azureOperations.uploadCustConsent(user.getId());
			final String housePhotoPath = azureOperations.uploadHousePhotoPath(user.getId());
			
			user.setConsentformpath(consentFormPath);
			user.setHousephotopath(housePhotoPath);
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
		Page<User> allsorted = userDao.findAllByusertype(pageRequest, type.name());
	
		return allsorted;
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		return userDao.findLoginUser(username);
	}

	@Override
	public Optional<User> getUserByPrimaryphone(String primaryphone) {
		
		if (!userDao.findByPrimaryphone(primaryphone).isPresent()) {
			throw new ItemNotFoundException(primaryphone);
		} else {
			return userDao.findByPrimaryphone(primaryphone);
		}
		
	}

	@Override
	public OperationsStatusModel forgotPasswordRequest(String username) {
		Optional<User> user = userDao.findByUsername(username);
		
		OperationsStatusModel operationsStatusModel = new OperationsStatusModel();
		operationsStatusModel.setOperationName(Operations.SEND_MAIL.name());
		operationsStatusModel.setOperationResult(OperationsResults.FAILED.name());

		if (!user.isPresent()) {
			throw new ItemNotFoundException("user not found");
		}

		String token = jwtTokenUtil.generatePasswordRestToken(user.get().getId());
		
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();

		passwordResetToken.setToken(token);
		passwordResetToken.setUser(user.get());

		try {
			emailService.sendPasswordResetRequest(token, user.get());
			passwordResetTokenRepository.save(passwordResetToken);
			operationsStatusModel.setOperationName(Operations.PASSWORD_RESET.name());
			operationsStatusModel.setOperationResult(OperationsResults.SUCCESS.name());
			
		} catch (MailException e) {
			logger.info("Mail not sent");
		}
		return operationsStatusModel;

	}
	
	@Override
	public User changePassword(String userId, String oldPassword, String newPassword) {
		
		Optional<User> user = userDao.findById(userId);
		
		if(!user.isPresent()) {
			throw new ItemNotFoundException(userId); 
		}
		String dbPassword = user.get().getPassword();
		
		
		if(passwordEncoder.matches(newPassword, dbPassword)) {
			throw new RuntimeException("Operation is invalid");
		}
		
		if(!passwordEncoder.matches(oldPassword, dbPassword)) {
			throw new RuntimeException("old password has no match");
		}
		
		user.get().setPassword(passwordEncoder.encode(newPassword));
		user.get().setPasswordreseton(new Date());
		userDao.save(user.get());
		
		user.get().setPassword(null);
		
		return user.get();
	}

	@Override
	public void updateLastLogon(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User approveUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public OperationsStatusModel passwordReset(PasswordResetModel passwordResetModel) {
		
		OperationsStatusModel operationsStatusModel = new OperationsStatusModel();
		String token = passwordResetModel.getToken();
		String password = passwordResetModel.getPassword();
		
		if(jwtTokenUtil.isTokenExpired(token)) {
			throw new RuntimeException("expired request, try again");
		}
		
		Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);
		
		if(!passwordResetToken.isPresent()) {
			throw new RuntimeException("Item not found");
		}
		
		User user = passwordResetToken.get().getUser();
		
		if(user != null) {
			user.setPassword(passwordEncoder.encode(password));
			userDao.save(user);
			
			operationsStatusModel.setOperationName(Operations.PASSWORD_RESET.name());
			operationsStatusModel.setOperationResult(OperationsResults.SUCCESS.name());
			
			passwordResetTokenRepository.delete(passwordResetToken.get());
			/*
			 * record system event
			 * */
			
		}else {
			operationsStatusModel.setOperationResult(OperationsResults.FAILED.name());
		}
		
		return operationsStatusModel;
	}
	
	public boolean validateUserDetails(User user) {

		if (userDao.findByUsername(user.getUsername()).isPresent()) {
			throw new ValueTakenException(user.getUsername(), "Username");
		} else if (userDao.findByEmail(user.getEmail()).isPresent()) {
			throw new ValueTakenException(user.getEmail(), "Email");
		} else if (userDao.findByPrimaryphone(user.getPrimaryphone()).isPresent()) {
			throw new ValueTakenException(user.getPrimaryphone(), "phone number");
		} else {
			return true;
		}
	}

}
