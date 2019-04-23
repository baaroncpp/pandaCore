package com.fredastone.pandacore.exception;

public class SaleNotFoundException extends ItemNotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaleNotFoundException(String itemId) {
		super(itemId);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Either sale was not found or sale is not in pending state";
	}

}
