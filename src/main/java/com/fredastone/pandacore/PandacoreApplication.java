package com.fredastone.pandacore;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PandacoreApplication{// extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PandacoreApplication.class, args);
	}
	
	
//	//TODO Remove if packaging as non war
//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(PandacoreApplication.class);
//    }

	
}
