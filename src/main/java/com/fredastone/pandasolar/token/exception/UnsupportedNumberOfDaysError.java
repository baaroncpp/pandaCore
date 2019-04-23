package com.fredastone.pandasolar.token.exception;

import com.fredastone.pandasolar.token.Constants;

public class UnsupportedNumberOfDaysError extends RuntimeException{

	/**
	 * 
	 */
	final String message = "You can only generate tokens for a minimum of %d and a maximum of %d days";
	
	private static final long serialVersionUID = -6176319599970684225L;

	@Override
	public String getMessage() {
		
		return String.format(message, Constants.MINIMUM_DAYS_SUPPORTED,Constants.MAXIMUM_DAYS_SUPPORTED);
	}
	
	

}
