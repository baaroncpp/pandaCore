package com.fredastone.pandacore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Notification {
	private NotificationType type;
	private String subject;
	private String content;
	private String address; //Has to be a json
	
	public enum NotificationType{
		EMAIL,SMS
	}

	@Override
	public String toString() {
		return "{\"type\":\"" + type + "\", \"subject\":\"" + subject + "\", \"content\":\"" + content + "\", \"address\":\"" + address+"\"}";
	}
	
}
