package com.fredastone.commons.logging.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fredastone.commons.logging.bean.RuntimeLogInfo;
import com.fredastone.commons.logging.utils.LoggerNameProperties;

@Component
public class RuntimeLog implements com.fredastone.commons.logging.RuntimeLog{

	
	private final LoggerNameProperties loggerNames;
	private final Logger logger;
	
	@Autowired
	public RuntimeLog(LoggerNameProperties loggerNames) {
		this.loggerNames = loggerNames;
		logger = LogManager.getLogger(this.loggerNames.getRuntimeLogger());
	}
	
	@Override
	public void info(String timestamp, String operation, String traceUniqeId, String message) {
		
		final RuntimeLogInfo log = new RuntimeLogInfo(timestamp, operation, traceUniqeId , message);
		if(logger.isInfoEnabled()) {
			logger.log(Level.INFO,log);
		}
		
	}

	@Override
	public void warn(String timestamp, String operation, String traceUniqeId, String message) {
		
		final RuntimeLogInfo info = new RuntimeLogInfo(timestamp, operation, traceUniqeId, message);
		
		if(logger.isWarnEnabled()) {
			logger.log(Level.WARN,info);
		}
	}
}
