package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

public class UserAccount {
	private String id;
	private String firstName;
	private String lastName;
	private String otherNames;
	private String mobileNumber;
	private String email;
	private short isEnabled;
	private String meterNumber;
	private short isTransactional;
	private Timestamp createdOn;
	
	
	public short getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(short isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getOtherNames() {
		return otherNames;
	}
	public void setOtherNames(String otherNames) {
		this.otherNames = otherNames;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public short getIsTransactional() {
		return isTransactional;
	}
	public void setIsTransactional(short isTransactional) {
		this.isTransactional = isTransactional;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", otherNames="
				+ otherNames + ", mobileNumber=" + mobileNumber + ", email=" + email + ", isTransactional="
				+ isTransactional + ", createdOn=" + createdOn + "]";
	}

}
