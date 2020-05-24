package com.fredastone.pandacore.service.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.constants.ServiceConstants;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.entity.ApprovalReview;
import com.fredastone.pandacore.entity.Approver;
import com.fredastone.pandacore.entity.Capex;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.entity.Opex;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.entity.VSaleApprovalReview;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.exception.SaleNotFoundException;
import com.fredastone.pandacore.models.ApprovalModel;
import com.fredastone.pandacore.models.Notification;
import com.fredastone.pandacore.models.Notification.NotificationType;
import com.fredastone.pandacore.repository.AgentMetaRepository;
import com.fredastone.pandacore.repository.ApprovalReviewRepository;
import com.fredastone.pandacore.repository.ApproverRepository;
import com.fredastone.pandacore.repository.CapexRepository;
import com.fredastone.pandacore.repository.EmployeeRepository;
import com.fredastone.pandacore.repository.OpexRepository;
import com.fredastone.pandacore.repository.RoleRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.repository.UserRoleRepository;
import com.fredastone.pandacore.repository.VSaleApprovalReviewRepository;
import com.fredastone.pandacore.service.ApprovalService;
import com.fredastone.pandacore.service.SaleService;
import com.fredastone.pandacore.util.ServiceUtils;

@Transactional
@Service
public class ApprovalServiceImpl implements ApprovalService {
	
	@Value("${notification.exchange.name}")
	private String notificationExchange;

	@Value("${notification.queue.email.name}")
	private String emailQueue;

	@Value("${notification.queue.sms.name}")
	private String smsQueue;

	@Value("${notification.routing.sms.key}")
	private String smsRoutingKey;

	@Value("${notification.routing.email.key}")
	private String emailRoutingKey;
	
	@Value("{notification.message.userapproval}")
	private String userApprovalMessage;
	
	@Value("{notification.message.passwordresetrequestmessage}")
	private String passwordResetMessage;
	
	@Value("{notification.message.saleapproval}")
	private String saleApprovalMessage;
	
	@Value("{notification.message.capexapproval}")
	private String capexApprovalMessage;
	
	@Value("{notification.message.opexapproval}")
	private String opexApprovalMessage;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	private ApproverRepository approverDao;
	private ApprovalReviewRepository approvalReviewDao;
	private UserRepository userDao;
	private EmployeeRepository employeeDao;
	private AgentMetaRepository agentDao;
	private VSaleApprovalReviewRepository saleReviewRepo;
	private RoleRepository roleRepository;
	private UserRoleRepository userRoleRepository;
	private SaleRepository saleRepository;
	private CapexRepository capexRepository;
	private OpexRepository opexRepository;
	private SaleService saleService;
	
	private static final String CUSTOMER_USER_TYPE = "customer";
	private static final String EMPLOYEE_USER_TYPE = "employee";
	private static final String AGENT_USER_TYPE = "agent";
	
	@Autowired
	public ApprovalServiceImpl(
			SaleRepository saleRepository,
			UserRoleRepository userRoleRepository,
			RoleRepository roleRepository,
			ApprovalReviewRepository approvalReviewDao,
			UserRepository userDao,
			EmployeeRepository employeeDao,
			AgentMetaRepository agentDao,
			ApproverRepository approverDao,
			OpexRepository opexRepository,
			CapexRepository capexRepository,
			VSaleApprovalReviewRepository saleReviewRepo, SaleService saleService) {
		
		this.approvalReviewDao = approvalReviewDao;
		this.userDao = userDao;
		this.employeeDao = employeeDao;
		this.agentDao = agentDao;
		this.approverDao = approverDao;
		this.saleReviewRepo = saleReviewRepo;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.saleRepository = saleRepository;
		this.capexRepository = capexRepository;
		this.opexRepository = opexRepository;
		this.saleService = saleService;
	}
	

	@Override
	public User approveUser(String userId,String approverId) {
		Optional<User> user = this.userDao.findById(userId);
		
		if(!user.isPresent())
			throw new RuntimeException("User with id "+userId+" not found in system");
		
		if(user.get().isIsapproved() || user.get().getUsertype().toLowerCase().equals(CUSTOMER_USER_TYPE))
			throw new RuntimeException("Operation is not supported for user with id "+userId);

		
		user.get().setIsapproved(Boolean.TRUE);
		user.get().setIsactive(Boolean.TRUE);
		
		if(user.get().getUsertype().toLowerCase().equals(EMPLOYEE_USER_TYPE))
		{
			Optional<EmployeeMeta> employeeMeta = this.employeeDao.findById(userId);
			if(!employeeMeta.isPresent()) {
				throw new RuntimeException("No Employee additinal info found");
			}
			
			employeeMeta.get().setIsapproved(Boolean.TRUE);
			employeeMeta.get().setIsterminated(Boolean.FALSE);
			
			this.employeeDao.save(employeeMeta.get());
			userRoleRepository.save(addDefaultUserRole(user.get()));
			
		}
		
		/*if(user.get().getUsertype().toLowerCase().equals(CUSTOMER_USER_TYPE)) {
			Optional<CustomerMeta> customerMeta = customerMetaRepository.findById(user.get().getId());
			if(!customerMeta.isPresent()) {
				throw new RuntimeException("No Agent additonal info found");
			}
			
			final ApprovalReview review = ApprovalReview.builder()
					.createdon(new Date())
					.itemid(user.get().getId())
					.reviewtype(2)
					.review("Approved").build();
					
			
			this.addApprovalReview(review);
			
			agentDao.save(agentMeta.get());
			userRoleRepository.save(addNApproveDefaultUserRole(user.get()));
		}*/
		
		if(user.get().getUsertype().toLowerCase().equals(AGENT_USER_TYPE)) {
			Optional<AgentMeta> agentMeta = this.agentDao.findById(userId);
			if(!agentMeta.isPresent()) {
				throw new RuntimeException("No Agent additonal info found");
			}
			
			agentMeta.get().setIsactive(Boolean.TRUE);
			agentMeta.get().setIsdeactivated(Boolean.FALSE);
			
			final ApprovalReview review = ApprovalReview.builder()
					.createdon(new Date())
					.itemid(user.get().getId())
					.reviewtype(2)
					.review("Approved").build();
					
			
			this.addApprovalReview(review);
			
			agentDao.save(agentMeta.get());
			
			Optional<UserRole> ur = userRoleRepository.findByUserAndRole(user.get(), addDefaultUserRole(user.get()).getRole());
			if(ur.isPresent()) {
				throw new RuntimeException(user.get().getUsername()+ " already has the specified role");
			}
			userRoleRepository.save(addDefaultUserRole(user.get()));
		}
		
		
		final Approver approver = Approver.builder()
				.id(ServiceUtils.getUUID())
				.createdon(new Date())
				.userid(approverId)
				.itemapproved("user")
				.itemid(user.get().getId()).build();
		
		approverDao.save(approver);
		
		//approval mail notification
		Notification notify = Notification.builder()
				.type(NotificationType.EMAIL)
				.subject("PANDA SOLAR ACCOUNT APPROVED")
				.address(user.get().getCompanyemail())
				.content(String.format(userApprovalMessage, user.get().getFirstname()+" "+user.get().getLastname())).build();
		
		rabbitTemplate.convertAndSend(notificationExchange,emailRoutingKey,notify.toString());
		
		
		return this.userDao.save(user.get());
	}

	@Override
	public ApprovalReview addApprovalReview(ApprovalReview approvalReview) {
		
		approvalReview.setId(ServiceUtils.getUUID());
		return approvalReviewDao.save(approvalReview);
		
	}

	@Override
	public List<ApprovalReview> getAllApprovalReview(String id) {
		
		return approvalReviewDao.findAllByapprovalid(id);

	}


	@Override
	public List<VSaleApprovalReview> getSaleApprovalReviewByAgent(String agentid, String itemid) {
		return saleReviewRepo.getAllSaleApprovalReview(agentid, itemid);
	}
	
	//assigns default userrole when user is approved n activated
	public UserRole addDefaultUserRole(User user) {
		String userType = user.getUsertype();
		UserRole userRole = new UserRole();
		
		userRole.setId(ServiceUtils.getUUID());
		
		switch(userType) {
		case "CUSTOMER":
			userRole.setRole(roleRepository.findByName(RoleName.ROLE_CUSTOMER).get());
			userRole.setUser(user);
			userRole.setCreatedon(new Date());
			break;
		case "AGENT":
			userRole.setRole(roleRepository.findByName(RoleName.ROLE_AGENT).get());
			userRole.setUser(user);
			userRole.setCreatedon(new Date());
			break;
		case "EMPLOYEE":
			userRole.setRole(roleRepository.findByName(RoleName.ROLE_EMPLOYEE).get());
			userRole.setUser(user);
			userRole.setCreatedon(new Date());
			break;
		}
		return userRole;
	}

	@Override
	public User deactivateUser(String userId, String approverId) {
		
		Optional<User> user = this.userDao.findById(userId);
		
		if(!user.isPresent()) {
			throw new RuntimeException("User with id "+userId+" not found in system");
		}
		
		if(!user.get().isIsactive())
			throw new RuntimeException("Operation is not supported for user with id "+userId);
		
		user.get().setIsactive(Boolean.FALSE);
		userDao.save(user.get());
		
		final ApprovalReview review = ApprovalReview.builder()
				.createdon(new Date())
				.itemid(user.get().getId())
				.reviewtype(2)
				.review("User deactivated").build();				
		
		this.addApprovalReview(review);
		
		final Approver approver = Approver.builder()
				.id(ServiceUtils.getUUID())
				.createdon(new Date())
				.userid(approverId)
				.itemapproved("USER")
				.itemid(user.get().getId()).build();
		
		approverDao.save(approver);
		
		return user.get();
	}

	@Override
	public Sale approveLeaseSale(String approverId, String saleId, String reviewDescription, short saleStatus) {
		Optional<User> user = userDao.findById(approverId);
		List<UserRole> userRoles = userRoleRepository.findAllByUser(user.get());
		
		String userType = user.get().getUsertype();
		
		if(userType.equals(UserType.EMPLOYEE.name())) {
			
			for(UserRole object : userRoles) {
				if(object.getRole().getName().equals(RoleName.ROLE_MANAGER) || object.getRole().getName().equals(RoleName.ROLE_SENIOR_MANAGER)) {
					
					 Optional<Sale> sale = saleRepository.findById(saleId);
					  
					  if(!sale.isPresent()) { throw new SaleNotFoundException(saleId); }
					  
					  if( sale.get().getSaletype().equals("Direct")) { 
						  throw new RuntimeException("Sale does not qualify for this operation"); 
					  }
					  
					  if( sale.get().getSalestatus() == (short) ServiceConstants.ACCEPTED_APPROVAL) { 
						  throw new RuntimeException("Sale has already been approved"); 
					  }
					
					  break;
				}
			}
			
		}else {
			throw new RuntimeException("Not authorized for this OPERATION");
		}
		
		  
		  
		 /* sale.get().setIsreviewed(Boolean.TRUE); sale.get().setSalestatus(saleStatus);
		 * 
		 * final ApprovalReview review = ApprovalReview.builder() .createdon(new Date())
		 * .itemid(saleId) .reviewtype(6) .review(reviewDescription).build();
		 * 
		 * this.addApprovalReview(review);
		 * 
		 * final Approver approver = Approver.builder() .id(ServiceUtils.getUUID())
		 * .createdon(new Date()) .userid(approverId) .itemapproved("SALE")
		 * .itemid(saleId).build();
		 * 
		 * approverDao.save(approver);
		 * 
		 * //add notification Optional<User> agentUser =
		 * userDao.findById(sale.get().getAgentid()); Optional<User> customerUser =
		 * userDao.findById(sale.get().getCustomerid()); Optional<User> approverUser =
		 * userDao.findById(approverId);
		 * 
		 * String agentName =
		 * agentUser.get().getFirstname()+" "+agentUser.get().getLastname(); String
		 * customerName =
		 * customerUser.get().getFirstname()+" "+customerUser.get().getLastname();
		 * String approverName =
		 * approverUser.get().getFirstname()+" "+approverUser.get().getLastname();
		 * 
		 * if(!agentUser.isPresent()) { throw new
		 * RuntimeException("Sale agent of ID: "+sale.get().getAgentid()
		 * +" does not exist "); }
		 * 
		 * //notify sale approval Notification notify = Notification.builder()
		 * .type(NotificationType.EMAIL) .subject("PANDA SOLAR SALE APPROVED")
		 * .address(agentUser.get().getCompanyemail())
		 * .content(String.format(saleApprovalMessage, agentName, customerName,
		 * approverName)).build();
		 * 
		 * rabbitTemplate.convertAndSend(notificationExchange,emailRoutingKey,notify.
		 * toString());
		 */
		return saleService.completeSale(saleId);
		
		//return saleRepository.save(sale.get());
	}

	@Transactional
	@Override
	public Capex approveCapex(ApprovalModel approvalModel) {
		
		Optional<Capex> capex = capexRepository.findById(approvalModel.getItemId());
		Optional<User> approveUser = userDao.findById(approvalModel.getApproverId());
		Optional<User> capexUser = userDao.findById(capex.get().getTEmployees().getUserid());
		
		if(!capex.isPresent()) {
			throw new ItemNotFoundException(approvalModel.getItemId());
		}else if(capex.get().getIsapproved() == Boolean.TRUE) {
			throw new RuntimeException("Capex does not qualify for this operation");
		}
		
		if(!approveUser.isPresent()) {
			throw new ItemNotFoundException(approvalModel.getApproverId());
		}
		
		capex.get().setApprovedon(new Date());
		capex.get().setIsapproved(Boolean.TRUE);
		
		final ApprovalReview review = ApprovalReview.builder()
				.createdon(new Date())
				.itemid(approvalModel.getItemId())
				.reviewtype(5)
				.review(approvalModel.getDescription()).build();				
		
		final Approver approver = Approver.builder()
				.id(ServiceUtils.getUUID())
				.createdon(new Date())
				.userid(approvalModel.getApproverId())
				.itemapproved("CAPEX")
				.itemid(approvalModel.getItemId()).build();
		
		String capexDate = dateToString(capex.get().getCreatedon());
		String approverName = approveUser.get().getFirstname()+" "+approveUser.get().getLastname();
		String employeeName = capexUser.get().getFirstname()+" "+capexUser.get().getLastname();
		String capexType = capex.get().getTCapexType().getName();
		
		//notify capex approval
		Notification notify = Notification.builder()
				.type(NotificationType.EMAIL)
				.subject("PANDA SOLAR CAPEX APPROVED")
				.address(capexUser.get().getCompanyemail())
				.content(String.format(capexApprovalMessage, employeeName, capexDate, capexType, approverName)).build();
		
		rabbitTemplate.convertAndSend(notificationExchange,emailRoutingKey,notify.toString());
		
		this.addApprovalReview(review);
		approverDao.save(approver);
		capexRepository.save(capex.get());
		
		return capex.get();
	}

	@Transactional
	@Override
	public Opex approveOpex(ApprovalModel approvalModel) {
		
		Optional<Opex> opex = opexRepository.findById(approvalModel.getItemId());
		Optional<User> approverUser = userDao.findById(approvalModel.getApproverId());
		Optional<User> empUser = userDao.findById(opex.get().getTEmployees().getUserid());
		
		if(!opex.isPresent()) {
			throw new ItemNotFoundException(approvalModel.getItemId());
		}else if(opex.get().getIsapproved() == Boolean.TRUE) {
			throw new RuntimeException("Opex does not qualify for this operation");
		}
		
		if(!approverUser.isPresent()) {
			throw new ItemNotFoundException(approvalModel.getApproverId());
		}
		
		opex.get().setIsapproved(Boolean.TRUE);
		opex.get().setApprovedon(new Date());
		
		String approverName = approverUser.get().getLastname()+" "+approverUser.get().getFirstname();
		String empName = empUser.get().getLastname()+" "+empUser.get().getFirstname();
		String opexDate = dateToString(opex.get().getCreatedon());
		String opexType = opex.get().getTOpexType().getName();
		
		final ApprovalReview review = ApprovalReview.builder()
				.createdon(new Date())
				.itemid(approvalModel.getItemId())
				.reviewtype(7)
				.review(approvalModel.getDescription()).build();				
		
		final Approver approver = Approver.builder()
				.id(ServiceUtils.getUUID())
				.createdon(new Date())
				.userid(approvalModel.getApproverId())
				.itemapproved("OPEX")
				.itemid(approvalModel.getItemId()).build();
		
		//notify opex approval
		Notification notify = Notification.builder()
				.type(NotificationType.EMAIL)
				.subject("PANDA SOLAR OPEX APPROVED")
				.address(empUser.get().getCompanyemail())
				.content(String.format(capexApprovalMessage, empName, opexDate, opexType, approverName)).build();
		
		rabbitTemplate.convertAndSend(notificationExchange,emailRoutingKey,notify.toString());
		
		this.addApprovalReview(review);
		approverDao.save(approver);
		opexRepository.save(opex.get());
		
		return null;
	}
	
	private String dateToString(Date date) {
		Format formatter = new SimpleDateFormat("dd MMMM yyyy");
		return formatter.format(date);
	}

}
