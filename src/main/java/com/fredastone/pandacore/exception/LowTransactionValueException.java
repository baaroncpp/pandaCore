package com.fredastone.pandacore.exception;

public class LowTransactionValueException extends RuntimeException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String message;
	
	public LowTransactionValueException(String message) {
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
