package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

public class TokenActivations {
	
	private String token;
	private String activatedOn;
	private String user_id;
	private String channelCode;
	private Timestamp createdOn;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getActivatedOn() {
		return activatedOn;
	}
	public void setActivatedOn(String activatedOn) {
		this.activatedOn = activatedOn;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "TokenActivations [token=" + token + ", activatedOn=" + activatedOn + ", user_id=" + user_id
				+ ", channelCode=" + channelCode + ", createdOn=" + createdOn + "]";
	}
	
	

}
