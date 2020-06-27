package com.fredastone.pandacore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class PandacoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PandacoreApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
//
//    @Bean
//    CommandLineRunner init(StorageService storageService) {
//        return (args) -> {
//           
//          AzureOperations ops = new AzureOperations();
//          ops.createAzureAccessToken("agent", "testimage2.jpg");
//        };
//    }

}
