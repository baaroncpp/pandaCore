package com.fredastone.pandacore.pandacore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fredastone.commons.logging.utils.LoggerNameProperties;

@Configuration
@ComponentScan("com.fredastone.commons") 
public class InitialConfiguration {
	
//
//	@Autowired
//	private LoggerNameProperties loggerNameProperties;
//	
//	@Bean
//	public LoggerNameProperties getLoggerNameProperties(){
//		return loggerNameProperties;
//	}

}
