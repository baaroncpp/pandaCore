package com.fredastone.pandacore.exception;

public class ItemNotFoundException extends RuntimeException {
	

	/**
 * 
 */
private static final long serialVersionUID = 1L;

	public ItemNotFoundException(String itemId) {
		// TODO Auto-generated constructor stub
		super(String.format("Item with id %s not found", itemId));

	}

	

}