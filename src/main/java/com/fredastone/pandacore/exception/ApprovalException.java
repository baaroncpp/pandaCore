package com.fredastone.pandacore.exception;

public class ApprovalException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public ApprovalException(String role) {
			// TODO Auto-generated constructor stub
			super(String.format("User with role %s has to approve", role));

		}


}
