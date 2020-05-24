package com.fredastone.pandacore.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.AndroidTokens;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.AndroidTokenRepository;
import com.fredastone.pandacore.repository.UserRepository;
import com.fredastone.security.JwtTokenUtil;

@RestController
@RequestMapping(path="v1/android")
public class AndroidNotificationsController {
	
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

	private AndroidTokenRepository repository;
	private UserRepository userRepository;
	
	@Autowired
	public  AndroidNotificationsController(AndroidTokenRepository repository, UserRepository userRepository) {
		// TODO Auto-generated constructor stub
		this.repository  = repository;
		this.userRepository = userRepository;
	}
	
	/*
	 * //TODO Use id from authentication object
	 * 
	 * @RequestMapping(path="/token/new",method = RequestMethod.POST) public
	 * ResponseEntity<?> postNewDirectSale(@RequestBody AndroidTokens token) {
	 * 
	 * return ResponseEntity.ok(repository.save(token));
	 * 
	 * }
	 */
	
	@RequestMapping(path="/token/{token}",method = RequestMethod.POST)
	public ResponseEntity<?> registerAndroidDeviceToken(HttpServletRequest request, @PathVariable("token") String deviceToken){
		
		String id = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
		
		if(!userRepository.findById(id).isPresent()) {
			throw new ItemNotFoundException(id);
		}
		
		AndroidTokens androidToken = new AndroidTokens();
		androidToken.setUserid(id);
		androidToken.setCreatedon(new Date());
		androidToken.setToken(deviceToken);
		
		return ResponseEntity.ok(repository.save(androidToken));
	}
	
	/*
	 * @RequestMapping(path="/token/{id}",method = RequestMethod.GET) public
	 * ResponseEntity<?> postNewDirectSale(@PathVariable("id") String userid) {
	 * 
	 * return ResponseEntity.ok(repository.findById(userid));
	 * 
	 * }
	 */
	
}
