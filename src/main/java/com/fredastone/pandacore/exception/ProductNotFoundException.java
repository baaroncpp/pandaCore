package com.fredastone.pandacore.exception;

public class ProductNotFoundException extends ItemNotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final private String productId ;
	public ProductNotFoundException(String itemId) {
		super(itemId);
		this.productId = itemId;
	}
	
	@Override
	public String getMessage() {
		return String.format(" Product with id %s could not be located", productId);
	}

}
