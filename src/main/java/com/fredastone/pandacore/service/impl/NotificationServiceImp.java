package com.fredastone.pandacore.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredastone.pandacore.azure.IAzureOperations;
import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.constants.UserType;
import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.entity.AndroidTokens;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.Product;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.fcm.AndroidPushNotificationService;
import com.fredastone.pandacore.models.SaleModel;
import com.fredastone.pandacore.repository.AgentMetaRepository;
import com.fredastone.pandacore.repository.AndroidTokenRepository;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
import com.fredastone.pandacore.repository.ProductsRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.pandacore.repository.UserRoleRepository;
import com.fredastone.pandacore.service.NotificationService;
import com.microsoft.azure.storage.core.Logger;

@Service
public class NotificationServiceImp implements NotificationService {
	
	//private final String TOPIC = "JavaSampleApproach";
	private AndroidTokenRepository androidTokenRepository;
	private UserRepository userRepository;
	private SaleRepository saleRepository;
	private AndroidPushNotificationService androidPushNotificationService;
	private IAzureOperations azureOperations;
	private AgentMetaRepository agentDao;
	private CustomerMetaRepository customerMetaRepository;
	private ProductsRepository productDao;
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	public NotificationServiceImp(AndroidTokenRepository androidTokenRepository, 
			IAzureOperations azureOperations,
	        AgentMetaRepository agentDao,
	        CustomerMetaRepository customerMetaRepository,
	        ProductsRepository productDao,
			UserRepository userRepository,
			SaleRepository saleRepository,
			UserRoleRepository userRoleRepository,
			AndroidPushNotificationService androidPushNotificationService) {
		
		this.androidTokenRepository = androidTokenRepository;
		this.userRepository = userRepository;
		this.saleRepository = saleRepository;
		this.androidPushNotificationService = androidPushNotificationService;
		this.azureOperations = azureOperations;
        this.agentDao = agentDao;
        this.customerMetaRepository = customerMetaRepository;
        this.productDao = productDao;
        this.userRoleRepository = userRoleRepository;
	}

	@Override
	public void approveSaleNotification(Sale sale) {
		
		try {
			
			List<User> adminUsers = userRepository.findAllByusertype(UserType.EMPLOYEE.name());
			
			for(User user : adminUsers) {
				
				System.out.println(user.getFirstname());
				
				Optional<User> usr = userRepository.findById(user.getId());
				List<UserRole> userRoles = userRoleRepository.findAllByUser(usr.get());
				
				if(!userRoles.isEmpty()) {
					System.out.println(userRoles.size());
					for(UserRole obj : userRoles) {
						System.out.println(obj.getRole().getName().name());
						if(obj.getRole().getName().equals(RoleName.ROLE_MANAGER) || obj.getRole().getName().equals(RoleName.ROLE_SENIOR_MANAGER)) {
							
							Optional<AndroidTokens> androidToken = androidTokenRepository.findById(user.getId());						
							if(androidToken.isPresent()) {
								
								JSONObject body = new JSONObject();
							    body.put("to", androidToken.get().getToken());
							    body.put("priority", "high");
							    
							    JSONObject notification = new JSONObject();
							    notification.put("title", "APPROVE SALE");
							    notification.put("body", "APPROVE SALE");
							    
							    body.put("notification", notification);
							    
							    String jsonStr = "";
							    ObjectMapper ob = new ObjectMapper();
							    try {
									jsonStr = ob.writeValueAsString(convertToSaleModel(sale));
									
								} catch (JsonProcessingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							    
								JSONObject dataJson = new JSONObject(jsonStr);
							    
							    body.put("data", dataJson);
							    
							    HttpEntity<String> request = new HttpEntity<>(body.toString());
							    
							    CompletableFuture<String> pushNotification = androidPushNotificationService.send(request);
							    CompletableFuture.allOf(pushNotification).join();
							 
							    try {
									String firebaseResponse = pushNotification.get();
									System.out.println(firebaseResponse);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							    break;
							}else {
								System.out.println("Device not registered: FCM");
							}											
						}
					}
				}
			}
			
		}catch(NullPointerException e) {
			Logger.error(null, "APPROVE SALE NOTIFICATION", e.getMessage());
		}
		
	}

	@Override
	public void paymentNotification(LeasePayment payment) {
		
		try {
			
			Optional<Sale> sale = saleRepository.findById(payment.getLeaseid());
			
			if(!sale.isPresent()) {
				throw new ItemNotFoundException(payment.getLeaseid());
			}
			
			Optional<AndroidTokens> androidToken = androidTokenRepository.findById(sale.get().getAgentid());
			
			if(androidToken.isPresent()) {
				
				JSONObject body = new JSONObject();
			    body.put("to", androidToken.get().getToken());
			    body.put("priority", "high");
			    
			    JSONObject notification = new JSONObject();
			    notification.put("title", "PAYMENT");
			    notification.put("body", "Lease Payment");
			    
			    body.put("notification", notification);
			    
			    String jsonStr = "";
			    ObjectMapper obj = new ObjectMapper();
			    try {
					jsonStr = obj.writeValueAsString(payment);
					
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    
				JSONObject dataJson = new JSONObject(jsonStr);
			    
			    body.put("data", dataJson);
			    
			    //Message message = new Message();
			    
			    HttpEntity<String> request = new HttpEntity<>(body.toString());
			    
			    CompletableFuture<String> pushNotification = androidPushNotificationService.send(request);
			    CompletableFuture.allOf(pushNotification).join();
			 
			    try {
			      String firebaseResponse = pushNotification.get();
			      System.out.println(firebaseResponse);
			      //return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
			    } catch (InterruptedException e) {
			      e.printStackTrace();
			    } catch (ExecutionException e) {
			      e.printStackTrace();
			    }
			}else {
				System.out.println("Device not registered: FCM");
			}
			
		}catch(NullPointerException e) {
			Logger.error(null, "PAYMENT NOTIFICATION", e.getMessage());
		}
		
		
	    //return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}

	@Override
	public void approvedSaleNotification(Sale sale) {
		
		try {
			
			Optional<AndroidTokens> androidToken = androidTokenRepository.findById(sale.getAgentid());
			 
			if(androidToken.isPresent()) { 
				JSONObject body = new JSONObject();
			    body.put("to", androidToken.get().getToken());
			    body.put("priority", "high");
			    
			    JSONObject notification = new JSONObject();
			    notification.put("title", "SALE APPROVED");
			    notification.put("body", "SALE APPROVED");
			    
			    body.put("notification", notification);
			    
			    String jsonStr = "";
			    ObjectMapper obj = new ObjectMapper();
			    try {
					jsonStr = obj.writeValueAsString(convertToSaleModel(sale));
					
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    
				JSONObject dataJson = new JSONObject(jsonStr);
			    body.put("data", dataJson);
			    
			    HttpEntity<String> request = new HttpEntity<>(body.toString());
			    
			    System.out.println(body.toString());
			    
			    CompletableFuture<String> pushNotification = androidPushNotificationService.send(request);
			    CompletableFuture.allOf(pushNotification).join();
			 
			    try {
					String firebaseResponse = pushNotification.get();
					System.out.println(firebaseResponse);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else {
				System.out.println("Device not registered: FCM");
			}

			
		}catch(NullPointerException e) {
			Logger.error(null, "SALE APPROVED NOTIFICATION", e.getMessage());
		}		
				
	}
	
	public SaleModel convertToSaleModel(Sale sale) {

		SaleModel saleModel = new SaleModel();

		Optional<AgentMeta> agent = agentDao.findById(sale.getAgentid());
		if (!agent.isPresent()) {
			throw new ItemNotFoundException(sale.getAgentid());
		}

		agent.get().setProfilepath(azureOperations.getProfile(sale.getAgentid()));

		Optional<CustomerMeta> customer = customerMetaRepository.findById(sale.getCustomerid());
		if (!customer.isPresent()) {
			throw new ItemNotFoundException(sale.getCustomerid());
		}

		customer.get().setProfilephotopath(azureOperations.getProfile(sale.getCustomerid()));

		Optional<Product> product = productDao.findById(sale.getProductid());
		if (!product.isPresent()) {
			throw new ItemNotFoundException(sale.getProductid());
		}

		saleModel.setAgent(agent.get());
		saleModel.setCustomer(customer.get());
		saleModel.setProduct(product.get());
		saleModel.setAgentcommission(sale.getAgentcommission());
		saleModel.setAmount(sale.getAmount());
		saleModel.setCompletedon(sale.getCompletedon());
		saleModel.setCreatedon(sale.getCreatedon());
		saleModel.setDescription(sale.getDescription());
		saleModel.setId(sale.getId());
		saleModel.setIsreviewed(sale.isIsreviewed());
		saleModel.setLat(sale.getLat());
		saleModel.setLong_(sale.getLong_());
		saleModel.setQuantity(sale.getQuantity());
		saleModel.setSalestatus(sale.getSalestatus());
		saleModel.setSaletype(sale.getSaletype());
		saleModel.setScannedserial(sale.getScannedserial());

		return saleModel;
	}

}
