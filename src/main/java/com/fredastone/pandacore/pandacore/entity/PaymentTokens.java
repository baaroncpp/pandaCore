package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

public class PaymentTokens {
	
	private String id;
	private String token;
	private float value;
	private short isValid;
	private Timestamp createdOn;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public short getIsValid() {
		return isValid;
	}
	public void setIsValid(short isValid) {
		this.isValid = isValid;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "PaymentTokens [id=" + id + ", token=" + token + ", value=" + value + ", isValid=" + isValid
				+ ", createdOn=" + createdOn + "]";
	}
	
	

}
