package com.fredastone.pandasolar.token;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final Map<String, Integer> COMMAND_MAP_TABLE;
	public static final Map<String,String> DAYS_MAP_TABLE;
	private static final String PAY_KEY = "PAY";
	private static final String DEMOLISH_KEY = "DEMOLISH";
	private static final String CLEAR_LOAN_KEY = "CLEAR_LOAN";
	private static final String RESET_KEY = "RESET";
	private static final String UNLOCK_DEVICE_KEY = "UNLOCK";
	
	public static final int MAXIMUM_DAYS_SUPPORTED = 99;
	public static final int MINIMUM_DAYS_SUPPORTED = 1;
	public static final String OPERATION_KEY = "somekeyforencryp";
	
	static {
		COMMAND_MAP_TABLE = new HashMap<String, Integer>(5);
		
		COMMAND_MAP_TABLE.put(PAY_KEY, 0x9C);
		COMMAND_MAP_TABLE.put(DEMOLISH_KEY, 0x9D);
		COMMAND_MAP_TABLE.put(CLEAR_LOAN_KEY, 0x9E);
		COMMAND_MAP_TABLE.put(RESET_KEY, 0x9F);
		COMMAND_MAP_TABLE.put(UNLOCK_DEVICE_KEY, 0xA0);
		
		DAYS_MAP_TABLE = new HashMap<String, String>(10);
		
		DAYS_MAP_TABLE.put("0", "5");
		DAYS_MAP_TABLE.put("1", "6");
		DAYS_MAP_TABLE.put("2", "8");
		DAYS_MAP_TABLE.put("3", "9");
		DAYS_MAP_TABLE.put("4", "7");
		DAYS_MAP_TABLE.put("5", "0");
		DAYS_MAP_TABLE.put("6", "1");
		DAYS_MAP_TABLE.put("8", "2");
		DAYS_MAP_TABLE.put("9", "3");
		DAYS_MAP_TABLE.put("7", "4");

	}

	
}
