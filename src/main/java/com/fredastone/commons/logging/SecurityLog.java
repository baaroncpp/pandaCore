package com.fredastone.commons.logging;

public interface SecurityLog {

	void info(String userName,String resultInfo,String staticInfo,String externalInfo);
	void warn(String userName,String resultInfo,String staticInfo,String externalInfo);
	void error(String userName,String resultInfo,String staticInfo,String externalInfo);
	
}
