package com.fredastone.commons.logging;

public interface RuntimeLog {
	
	void info(String operation,String traceUniqeId,String message);
	void warn(String operation,String traceUniqeId,String message); 
	void error(String operation,String traceUniqeId,String message); 

}
