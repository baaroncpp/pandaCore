package com.fredastone.pandacore.exception;

public class ValueTakenException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public ValueTakenException(String value, String valueName) {
	
		super(String.format(valueName+" "+value +" taken, try another"));

	}

}
