package com.fredastone.pandacore.exception;

public class LeaseOfferNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Lease selected cannot be found. Select another lease offering";
	}

	
}
