package com.fredastone.commons.text;

public class StringUtil {

	
	public static String dealNull(String value) {
		return (value == null || value.isEmpty()) ? "" : value;
	}
}
