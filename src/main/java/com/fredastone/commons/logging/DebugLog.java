package com.fredastone.commons.logging;

public interface DebugLog {

	void debug(String type,String msisdn,String transactionId,String traceUniqueId,String classFileName,String classFileLine,String stackTrace);
	void info(String type,String msisdn,String transactionId,String traceUniqueId,String classFileName,String classFileLine,String stackTrace);
	void warn(String type,String msisdn,String transactionId,String traceUniqueId,String classFileName,String classFileLine,String stackTrace);
	void error(String type,String msisdn,String transactionId,String traceUniqueId,String classFileName,String classFileLine,String stackTrace);

}
