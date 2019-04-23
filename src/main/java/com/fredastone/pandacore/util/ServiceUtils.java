package com.fredastone.pandacore.util;

import java.util.UUID;

public class ServiceUtils {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
