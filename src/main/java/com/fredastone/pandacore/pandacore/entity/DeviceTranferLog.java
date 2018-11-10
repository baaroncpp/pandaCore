package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

public class DeviceTranferLog {
	
	private long id;
	private Timestamp ownedFrom;
	private Timestamp ownedTo;
	private String tranferedFromuserId;
	private String tranferedToUserId;
	private String userId;
	private String deviceId;
	private String tranferReason;
	private Timestamp createdOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getOwnedFrom() {
		return ownedFrom;
	}
	public void setOwnedFrom(Timestamp ownedFrom) {
		this.ownedFrom = ownedFrom;
	}
	public Timestamp getOwnedTo() {
		return ownedTo;
	}
	public void setOwnedTo(Timestamp ownedTo) {
		this.ownedTo = ownedTo;
	}
	public String getTranferedFromuserId() {
		return tranferedFromuserId;
	}
	public void setTranferedFromuserId(String tranferedFromuserId) {
		this.tranferedFromuserId = tranferedFromuserId;
	}
	public String getTranferedToUserId() {
		return tranferedToUserId;
	}
	public void setTranferedToUserId(String tranferedToUserId) {
		this.tranferedToUserId = tranferedToUserId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getTranferReason() {
		return tranferReason;
	}
	public void setTranferReason(String tranferReason) {
		this.tranferReason = tranferReason;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "DeviceTranferLog [id=" + id + ", ownedFrom=" + ownedFrom + ", ownedTo=" + ownedTo
				+ ", tranferedFromuserId=" + tranferedFromuserId + ", tranferedToUserId=" + tranferedToUserId
				+ ", userId=" + userId + ", deviceId=" + deviceId + ", tranferReason=" + tranferReason + ", createdOn="
				+ createdOn + "]";
	}
	

}
