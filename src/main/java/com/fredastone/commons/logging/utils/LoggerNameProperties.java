package com.fredastone.commons.logging.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class LoggerNameProperties 
{
	@Getter
	@Value("${customlogging.interfacelogger}")
	private String interfaceLogger;
	
	@Getter
	@Value("${customlogging.securitylogger}")
	private String securityLogger;
	
	@Getter
	@Value("${customlogging.debugLogger}")
	private String debugLogger;
	
	@Getter
	@Value("${customlogging.runtimelogger}")
	private String runtimeLogger;
	
	

}
