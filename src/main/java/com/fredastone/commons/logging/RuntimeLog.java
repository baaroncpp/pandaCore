package com.fredastone.commons.logging;

public interface RuntimeLog {
	
	void info(String timestamp,String operation,String traceUniqeId,String message);
	void warn(String timestamp,String operation,String traceUniqeId,String message); 

}
