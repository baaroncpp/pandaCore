package com.fredastone.pandacore.constants;

public enum ApprovalTypes {
	
	EMPLOYEE(1),
	EXPENSE(2),
	AGENT(3),
	EQUIPMENT_DISPATCH(4),
	USER_ROLE(5),
	LEASE_SALE(6);

	private final int index;
	ApprovalTypes(int index) {
		// TODO Auto-generated constructor stub
		this.index = index;
	}
	
	public int getValue() {
		return index;
	}

}
