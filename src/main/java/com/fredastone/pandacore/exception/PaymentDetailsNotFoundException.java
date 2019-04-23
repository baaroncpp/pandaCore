package com.fredastone.pandacore.exception;

public class PaymentDetailsNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4900645204204725911L;
	private String message;
	
	public PaymentDetailsNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}
}
