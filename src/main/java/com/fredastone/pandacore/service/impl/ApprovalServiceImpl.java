package com.fredastone.pandacore.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.entity.ApprovalReview;
import com.fredastone.pandacore.entity.Approver;
import com.fredastone.pandacore.entity.EmployeeMeta;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.entity.VSaleApprovalReview;
import com.fredastone.pandacore.exception.SaleNotFoundException;
import com.fredastone.pandacore.models.Notification;
import com.fredastone.pandacore.models.Notification.NotificationType;
import com.fredastone.pandacore.repository.AgentMetaRepository;
import com.fredastone.pandacore.repository.ApprovalReviewRepository;
import com.fredastone.pandacore.repository.ApproverRepository;
import com.fredastone.pandacore.repository.ConfigRepository;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
import com.fredastone.pandacore.repository.EmployeeRepository;
import com.fredastone.pandacore.repository.RoleRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.repository.UserRoleRepository;
import com.fredastone.pandacore.repository.VSaleApprovalReviewRepository;
import com.fredastone.pandacore.service.ApprovalService;
import com.fredastone.pandacore.util.ServiceUtils;

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
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	private ApproverRepository approverDao;
	private ApprovalReviewRepository approvalReviewDao;
	private UserRepository userDao;
	private EmployeeRepository employeeDao;
	private AgentMetaRepository agentDao;
	private VSaleApprovalReviewRepository saleReviewRepo;
	private ConfigRepository configDao;
	private RoleRepository roleRepository;
	private UserRoleRepository userRoleRepository;
	private CustomerMetaRepository customerMetaRepository;
	private SaleRepository saleRepository;
	
	private static final String CUSTOMER_USER_TYPE = "customer";
	private static final String EMPLOYEE_USER_TYPE = "employee";
	private static final String AGENT_USER_TYPE = "agent";
	
	@Autowired
	public ApprovalServiceImpl(
			SaleRepository saleRepository,
			CustomerMetaRepository customerMetaRepository,
			UserRoleRepository userRoleRepository,
			RoleRepository roleRepository,
			ApprovalReviewRepository approvalReviewDao,
			UserRepository userDao,
			EmployeeRepository employeeDao,
			AgentMetaRepository agentDao,
			ConfigRepository configDao,
			ApproverRepository approverDao,
			VSaleApprovalReviewRepository saleReviewRepo) {
		
		this.approvalReviewDao = approvalReviewDao;
		this.userDao = userDao;
		this.employeeDao = employeeDao;
		this.agentDao = agentDao;
		this.configDao = configDao;
		this.approverDao = approverDao;
		this.saleReviewRepo = saleReviewRepo;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.customerMetaRepository = customerMetaRepository;
		this.saleRepository = saleRepository;
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
		/*case "EMPLOYEE":
			userRole.setRole(roleRepository.findByName(RoleName.ROLE_EMPLOYEE).get());
			userRole.setUser(user);
			userRole.setIsActive(Boolean.TRUE);
			userRole.setCreatedon(new Date());
			break;*/
		}
		return userRole;
	}

/*
	public UserRole approveUserRole(String approverId, String userRoleId) {
		
		Optional<UserRole> userRole = userRoleRepository.findById(userRoleId);
		
		if(!userRole.isPresent()) {
			throw new ItemNotFoundException("");
		}
		
		if(userRole.get().getIsActive()) {
			throw new RuntimeException("Operation doesn't apply to this item");
		}
		
		userRole.get().setIsActive(Boolean.TRUE);
		
		final Approver approver = Approver.builder()
				.createdon(new Date())
				.id(ServiceUtils.getUUID())
				.itemapproved("role")
				.itemid(String.valueOf(userRole.get().getId()))
				.userid(approverId).build();
				
		
		approverDao.save(approver);
		
		final ApprovalReview review = ApprovalReview.builder()
				.createdon(new Date())
				.itemid(String.valueOf(userRole.get().getId()))
				.reviewtype(5)
				.review("Approved").build();				
		
		this.addApprovalReview(review);
		
		return userRole.get();
	}
*/
/*
	@Override
	public UserRole removeUserRole(String userRoleId, String approverId) {
		
		Optional<UserRole> userRole = userRoleRepository.findById(userRoleId);
		
		if(!userRole.isPresent()) {
			throw new ItemNotFoundException(userRoleId);
		}
		
		userRole.get().setIsActive(Boolean.FALSE);
		userRoleRepository.save(userRole.get());
		
		final Approver approver = Approver.builder()
				.createdon(new Date())
				.id(ServiceUtils.getUUID())
				.itemapproved("role")
				.itemid(String.valueOf(userRole.get().getId()))
				.userid(approverId).build();
				
		approverDao.save(approver);
		
		final ApprovalReview review = ApprovalReview.builder()
				.createdon(new Date())
				.itemid(String.valueOf(userRole.get().getId()))
				.reviewtype(5)
				.review("Role deactivated").build();				
		
		this.addApprovalReview(review);
		
		return userRole.get();
	}
*/	

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
		
		Optional<Sale> sale  = saleRepository.findById(saleId);
		
		if(!sale.isPresent()) {
			throw new SaleNotFoundException(saleId);
		}
		
		if( sale.get().getSaletype().equals("Direct") || sale.get().isIsreviewed() == Boolean.TRUE ) {
			throw new RuntimeException("Sale doe not qualify for this operation");
		}
		
		sale.get().setIsreviewed(Boolean.TRUE);
		sale.get().setSalestatus(saleStatus);
		
		final ApprovalReview review = ApprovalReview.builder()
				.createdon(new Date())
				.itemid(saleId)
				.reviewtype(6)
				.review(reviewDescription).build();				
		
		this.addApprovalReview(review);
		
		final Approver approver = Approver.builder()
				.id(ServiceUtils.getUUID())
				.createdon(new Date())
				.userid(approverId)
				.itemapproved("SALE")
				.itemid(saleId).build();
		
		approverDao.save(approver);
		
		//add notification
		Optional<User> agentUser = userDao.findById(sale.get().getAgentid());
		Optional<User> customerUser = userDao.findById(sale.get().getCustomerid());
		Optional<User> approverUser = userDao.findById(approverId);
		
		String agentName = agentUser.get().getFirstname()+" "+agentUser.get().getLastname();
		String customerName = customerUser.get().getFirstname()+" "+customerUser.get().getLastname();
		String approverName = approverUser.get().getFirstname()+" "+approverUser.get().getLastname();
		
		if(!agentUser.isPresent()) {
			throw new RuntimeException("Sale agent of ID: "+sale.get().getAgentid()+" does not exist ");
		}
		
		//notify sale approval
		Notification notify = Notification.builder()
				.type(NotificationType.EMAIL)
				.subject("PANDA SOLAR SALE APPROVED")
				.address(agentUser.get().getCompanyemail())
				.content(String.format(saleApprovalMessage, agentName, customerName, approverName)).build();
		
		rabbitTemplate.convertAndSend(notificationExchange,emailRoutingKey,notify.toString());
		
		return saleRepository.save(sale.get());
	}

}
