package com.fredastone.commons.logging.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredastone.commons.logging.bean.InterfaceLogInfo;
import com.fredastone.commons.logging.bean.InterfaceLogParamsMandatory;
import com.fredastone.commons.logging.bean.InterfaceLogParamsOptional;
import com.fredastone.commons.logging.utils.LoggerNameProperties;

@Component
public class InterfaceLog implements com.fredastone.commons.logging.InterfaceLog {
	
	private final LoggerNameProperties loggerNames;
	
	private final Logger logger;
	
	@Autowired
	public InterfaceLog(LoggerNameProperties loggerNames) {
		// TODO Auto-generated constructor stub
		this.loggerNames = loggerNames;
		this.logger = LogManager.getLogger(this.loggerNames.getInterfaceLogger());
	}

	@Override
	public void info(InterfaceLogParamsMandatory mandatoryParam, InterfaceLogParamsOptional optionalParam) {
		if(logger.isInfoEnabled()) {
			final InterfaceLogInfo ili = new InterfaceLogInfo(mandatoryParam, optionalParam);
			logger.info(ili);
			
		}
		
	}

	@Override
	public void warn(InterfaceLogParamsMandatory mandatoryParam, InterfaceLogParamsOptional optionalParam) {
		// TODO Auto-generated method stub
		if(logger.isWarnEnabled()) {
			final InterfaceLogInfo ili = new InterfaceLogInfo(mandatoryParam, optionalParam);
			logger.warn(ili);
		}
		
	}

	@Override
	public void error(InterfaceLogParamsMandatory mandatoryParam, InterfaceLogParamsOptional optionalParam) {
		// TODO Auto-generated method stub
		if(logger.isErrorEnabled()) {
			final InterfaceLogInfo ili = new InterfaceLogInfo(mandatoryParam, optionalParam);
			logger.error(ili);
		}
		
	}

}
