package com.fredastone.commons.logging.impl;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredastone.commons.logging.bean.DebugLogInfo;
import com.fredastone.commons.logging.utils.LoggerNameProperties;

@Component
public class DebugLog implements com.fredastone.commons.logging.DebugLog{

	private final LoggerNameProperties loggerNames;
	private Logger logger;
	
	@Autowired
	public DebugLog(LoggerNameProperties loggerNames) {
		// 
		this.loggerNames = loggerNames;
		this.logger = LogManager.getLogger(this.loggerNames.getDebugLogger());
	}
	
	@Override
	public void info(String type, String msisdn, String transactionId, String traceUniqueId,
			String classFileName,String classFileLine,String stackTrace) {
		if(logger.isDebugEnabled()) {
			final Message debugLog = new 
					DebugLogInfo( type, msisdn, transactionId, traceUniqueId, 
							classFileName, classFileLine, stackTrace);
		
			logger.log(Level.INFO, debugLog);
		}
		
	}

	@Override
	public void warn(String type, String msisdn, String transactionId, String traceUniqueId,
			String classFileName, String classFileLine, String stackTrace) {
		
		if(logger.isWarnEnabled()) {
			final Message debugLog = new 
					DebugLogInfo( type, msisdn, transactionId, traceUniqueId, 
							classFileName, classFileLine, stackTrace);
			
			logger.log(Level.WARN, debugLog);
		}
		
	}
	
	@Override
	public void error(String type, String msisdn, String transactionId, String traceUniqueId,
			String classFileName, String classFileLine, String stackTrace) {
		
		if(logger.isErrorEnabled()) {
			final Message debugLog = new 
					DebugLogInfo( type, msisdn, transactionId, traceUniqueId, 
							classFileName, classFileLine, stackTrace);
			
			logger.log(Level.ERROR, debugLog);
		}
		
		
	}

	@Override
	public void debug(String type, String msisdn, String transactionId, String traceUniqueId,
			String classFileName, String classFileLine, String stackTrace) {
		
		if(logger.isDebugEnabled()) {
			final Message debugLog = new 
					DebugLogInfo(type, msisdn, transactionId, traceUniqueId, 
							classFileName, classFileLine, stackTrace);
			
			logger.log(Level.ERROR, debugLog);
		}
		
	}

}
