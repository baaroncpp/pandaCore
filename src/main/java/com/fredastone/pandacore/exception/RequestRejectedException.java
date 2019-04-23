package com.fredastone.pandacore.exception;

public class RequestRejectedException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestRejectedException(String id) {
		super(String.format("Request with id %s has been rejected", id));
	}

}
