package com.fredastone.pandacore.constants;

public enum OpexType {

	TRANSPORT(1),
	UTILITIES(2),
	ITINFRA(3),
	MARKETING(4),
	MISCELLENEOUS(5);
	
	private final int index;
	
	OpexType(int index){
		this.index = index;
	}
	
	public int getValue() {
		return index;
	}
}
