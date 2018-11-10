package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

public class FeesCollected {

	private String id;
	private String deviceSerial;
	private float amount;
	private Timestamp createdOn;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceSerial() {
		return deviceSerial;
	}
	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "FeesCollected [id=" + id + ", deviceSerial=" + deviceSerial + ", amount=" + amount + ", createdOn="
				+ createdOn + "]";
	}
	
	
}
