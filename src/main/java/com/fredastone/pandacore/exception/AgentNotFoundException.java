package com.fredastone.pandacore.exception;

public class AgentNotFoundException extends ItemNotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String itemId;
	public AgentNotFoundException(String itemId) {
		super(itemId);
		// TODO Auto-generated constructor stub
		this.itemId = itemId;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return String.format("Agent with ID %s could not be located",itemId);
	}

}
