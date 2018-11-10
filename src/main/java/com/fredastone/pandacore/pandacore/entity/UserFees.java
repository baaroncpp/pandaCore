package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

public class UserFees {
	
	private String id;
	private float amount;
	private String userId;
	private String deviceSerial;
	private String description;
	private Timestamp dueDate;
	private String isCleared;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public String getIsCleared() {
		return isCleared;
	}
	public void setIsCleared(String isCleared) {
		this.isCleared = isCleared;
	}
	@Override
	public String toString() {
		return "UserFees [id=" + id + ", amount=" + amount + ", userId=" + userId + ", deviceSerial=" + deviceSerial
				+ ", description=" + description + ", dueDate=" + dueDate + ", isCleared=" + isCleared + "]";
	}

}
