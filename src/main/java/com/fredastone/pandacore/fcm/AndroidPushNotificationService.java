package com.fredastone.pandacore.fcm;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationService {
	
	@Value("${pandacore.fcm.base.url}")
	private String FCM_BASE_URL;
	
	@Value("${pandacore.fcm.server.key}")
	private String FCM_SERVER_KEY;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {
	 
	    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
	    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FCM_SERVER_KEY));
	    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
	    restTemplate.setInterceptors(interceptors);
	 
	    String firebaseResponse = restTemplate.postForObject(FCM_BASE_URL, entity, String.class);
	    return CompletableFuture.completedFuture(firebaseResponse);
	}

}
