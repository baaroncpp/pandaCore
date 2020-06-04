package com.fredastone.pandasolar.token;


import com.fredastone.pandasolar.token.exception.UnsupportedNumberOfDaysError;

public class TokenGenerator {
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private final static int RADIX = 16;
	private final static int CHECK_INDEX_1 = 6;
	private final static int CHECK_INDEX_2 = 3;
	private static TEA  tea = null;
	
	
	//final static String KEY = "0x789f5645, 0xf68bd5a4, 0x81963ffa, 0x458fac58"
//	private static final  byte[] KEY = new byte[] {
//      		0x78, (byte) 0x9f,0x56,0x45,
//      		(byte) 0xf6,(byte) 0x8b,(byte) 0xd5,(byte) 0xa4, 
//      		(byte) 0x81,(byte) 0x96,0x3f,(byte) 0xfa,
//      		0x45,(byte) 0x8f,(byte) 0xac,0x58
//              };
	
	private static final  byte[] KEY = new byte[] {
	0x75, (byte) 0x9f,(byte)0x96,0x78,
	(byte) 0xe6,(byte) 0x5b,(byte) 0xa5,(byte) 0xa4, 
	(byte) 0x23,(byte) 0x55,(byte)0xf3,(byte) 0xfb,
	0x21,(byte) 0xff,(byte) 0xa6,0x50
  };
	//Save correct UUID at point of adding device, will add this later
	static {
		try {
			tea = new TEA(KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String generateToken(String days,final CommandNames command, int times,final String uuid ) 
	throws UnsupportedNumberOfDaysError{
		
		if(!checkDaysSupported(days)) {
			throw new UnsupportedNumberOfDaysError();
		}
		
		final String plain1 = addPaddingHex(Integer.toHexString(Integer.valueOf(days)))
				+addPaddingHex(Integer.toHexString(Integer.valueOf(command.getCommandName())))+
				addPaddingHex(Integer.toHexString(Integer.valueOf(times)))
				+convertUUIDToHex(uuid);
		
//		
//		//--- Only possibility of changing here, taking hex instead of bytes
		final byte[] cipher1 = tea.encrypt_group(hexStringToByteArray(plain1));

		final String finalToken = 
				mapDays(addPaddingToDecimal(days))+
				addPaddingToDecimal(getStringFromHex(Integer.toHexString(Constants.COMMAND_MAP_TABLE.get(command.toString()))))+
				addPaddingToDecimal(String.valueOf(times))+ 
				addPaddingToDecimal(hexByteToString(cipher1[CHECK_INDEX_1]))+
				addPaddingToDecimal(hexByteToString(cipher1[CHECK_INDEX_2]));
		
		
		
		return finalToken;
			
	}
	
	private static boolean checkDaysSupported(String days) {
		final int mdays = Integer.valueOf(days);
		return (mdays >= Constants.MINIMUM_DAYS_SUPPORTED) && (mdays <= Constants.MAXIMUM_DAYS_SUPPORTED);
		
	}
	
    private static String mapDays(String day) {
    	
    	String finalDay = "";
    	for(int i = 0;i<day.length();i++) {
    		
    		finalDay = finalDay+Constants.DAYS_MAP_TABLE.get(Character.toString(day.charAt(i)));
    		
    	}
    	
    	return finalDay;
    }

	private static String getStringFromHex(String hex) {
		return Integer.valueOf(hex,RADIX)+"";
	}
    
	private static String addPaddingToDecimal(String value) {
		if(value.length() == 1) {
			return "00"+value;
		}else if(value.length() == 2) {
			return "0"+value;
		}
		
		return value;
	}
	
	//explaination for this gotten from https://www.baeldung.com/java-byte-arrays-hex-strings
	private static String hexByteToString(byte num) {
	    char[] hexDigits = new char[2];
	    hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
	    hexDigits[1] = Character.forDigit((num & 0xF), 16);
	    return Integer.valueOf(new String(hexDigits),16).toString();
	}

	public static String convertUUIDToHex(String uuid) {
		
		String hexString = "";
		int maxLength = uuid.length() -2;
	
		for(int i=0;i<=maxLength;) {
			int batch = Integer.valueOf(uuid.substring(i, i+3));
			hexString += addPaddingHex(Integer.toHexString(batch));
			
			i= i+3;
		}
		
		return hexString;
		
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
    
	private static String addPaddingHex(String value) {
		if(value.length() == 1) {
			return "0"+value;
		}
		
		return value;
	}
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
}
