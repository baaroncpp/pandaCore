package com.fredastone.commons.logging;

public interface DebugLog {

	void debug(String timestamp,String type,String msisdn,String transactionId,String traceUniqueId,String classFileName,String classFileLine,String stackTrace);
	void info(String timestamp,String type,String msisdn,String transactionId,String traceUniqueId,String classFileName,String classFileLine,String stackTrace);
	void warn(String timestamp,String type,String msisdn,String transactionId,String traceUniqueId,String classFileName,String classFileLine,String stackTrace);
	void error(String timestamp,String type,String msisdn,String transactionId,String traceUniqueId,String classFileName,String classFileLine,String stackTrace);

}
