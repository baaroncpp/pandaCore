package com.fredastone.pandacore.constants;

public enum CapexType {
	
	INFRASTRUCTURE(1),
	FURNITURE(2),
	EQUIPMENT_PURCHASE(3);
	
	private final int index;
	CapexType(int index){
		this.index = index;
	}
	
	public int getValue() {
		return index;
	}

}
