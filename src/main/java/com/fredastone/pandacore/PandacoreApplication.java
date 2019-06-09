package com.fredastone.pandacore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fredastone.pandacore.azure.AzureOperations;
import com.fredastone.pandacore.service.StorageService;

@SpringBootApplication
@EnableTransactionManagement
public class PandacoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PandacoreApplication.class, args);
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
