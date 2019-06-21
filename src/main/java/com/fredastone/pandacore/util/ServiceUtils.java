package com.fredastone.pandacore.util;

import java.util.UUID;

public class ServiceUtils {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	public static String reformatUGPhoneNumbers(String phoneNumber) {
		
		if(phoneNumber.startsWith("0")) {
			return phoneNumber.replaceFirst("0", "256");
		}else if(phoneNumber.startsWith("256"))
		{
			return phoneNumber;
		}else
		{
			return "256".concat(phoneNumber);
		}
	}

}
