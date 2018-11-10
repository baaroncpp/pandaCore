package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

public class DeviceUserMap {
	
	private long id;
	private String userId;
	private String deviceSerial;
	private Timestamp createdOn;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceSerial() {
		return deviceSerial;
	}
	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "DeviceUserMap [id=" + id + ", userId=" + userId + ", deviceSerial=" + deviceSerial + ", createdOn="
				+ createdOn + "]";
	}
	
	

}
