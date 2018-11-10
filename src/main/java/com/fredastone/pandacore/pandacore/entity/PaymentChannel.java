package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

public class PaymentChannel {
	private long id;
	private String channelName;
	private String channelCode;
	private int isEnabled;
	private Timestamp createdOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public int getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(int isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "PaymentChannel [id=" + id + ", channelName=" + channelName + ", channelCode=" + channelCode
				+ ", isEnabled=" + isEnabled + ", createdOn=" + createdOn + "]";
	}
	
	

}
