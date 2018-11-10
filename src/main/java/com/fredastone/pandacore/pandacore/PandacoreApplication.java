package com.fredastone.pandacore.pandacore;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@EnableTransactionManagement
public class PandacoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PandacoreApplication.class, args);
	}
	

	
}
