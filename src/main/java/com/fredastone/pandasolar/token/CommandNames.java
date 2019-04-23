package com.fredastone.pandasolar.token;

public enum CommandNames {

	PAY("1"), DEMOLISH("2"), CLEAR_LOAN("3"), RESET("4"), UNLOCK("5");

	private String commandName;

	public String getCommandName() {
		
		return this.commandName;
		
	}

	CommandNames(String commandName) {
		this.commandName = commandName;
	}

}
