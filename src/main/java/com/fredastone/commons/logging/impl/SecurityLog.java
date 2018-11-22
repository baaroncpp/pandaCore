package com.fredastone.commons.logging.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredastone.commons.logging.bean.SecurityLogInfo;
import com.fredastone.commons.logging.utils.LoggerNameProperties;

@Component
public class SecurityLog implements com.fredastone.commons.logging.SecurityLog{

	private final LoggerNameProperties loggerNames;
	private final Logger logger;
	
	@Autowired
	public SecurityLog(LoggerNameProperties loggerNames) {
		this.loggerNames = loggerNames;
		logger = LogManager.getLogger(this.loggerNames.getSecurityLogger());
	}
	
	@Override
	public void info(String userName, String resultInfo, String staticInfo, String externalInfo) {
		// TODO Auto-generated method stub
		if(this.logger.isInfoEnabled()) {
			final SecurityLogInfo info = new SecurityLogInfo(userName, resultInfo, staticInfo, externalInfo);
			logger.info(info);
		}
		
	}

	@Override
	public void warn(String userName, String resultInfo, String staticInfo, String externalInfo) {
		// TODO Auto-generated method stub
		if(this.logger.isWarnEnabled()) {
			final SecurityLogInfo info = new SecurityLogInfo(userName, resultInfo, staticInfo, externalInfo);
			logger.info(info);
			
		}
		
	}

	@Override
	public void error(String userName, String resultInfo, String staticInfo, String externalInfo) {
		// TODO Auto-generated method stub
		if(this.logger.isErrorEnabled()) {
			final SecurityLogInfo info = new SecurityLogInfo(userName, resultInfo, staticInfo, externalInfo);
			logger.info(info);
			
		}
		
	}

}
