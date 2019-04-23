package com.fredastone.pandasolar.token.exception;

public class UnsupportedSerialException extends RuntimeException{

	/**
	 * 
	 */
	String message;
	private static final long serialVersionUID = 1L;
	public UnsupportedSerialException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		this.message = message;
	}
	
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

}
