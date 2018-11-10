package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

import org.json.JSONObject;

public class AccountFinancialInformation {
	
	private String meterNumber;
	private String firstName;
	private String lastName;
	private String otherNames;
	private float balanceDue;
	private String email;
	private String mobileNumber;
	private int isTransactional;
	private float totalFees;
	private JSONObject fees;
	private Timestamp feesAddedOn;
	private Timestamp accountCreatedOn;
	private Timestamp feesDueDate;
	private String feesDescription;
	private short feesCleared;
	private String deviceSerial;
	private short activeAccount;
	private String accountId;
	
	
	public String getMeterNumber() {
		return meterNumber;
	}




	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
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




	public float getBalanceDue() {
		return balanceDue;
	}




	public void setBalanceDue(float balanceDue) {
		this.balanceDue = balanceDue;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getMobileNumber() {
		return mobileNumber;
	}




	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}




	public int getIsTransactional() {
		return isTransactional;
	}




	public void setIsTransactional(int isTransactional) {
		this.isTransactional = isTransactional;
	}




	public float getTotalFees() {
		return totalFees;
	}




	public void setTotalFees(float totalFees) {
		this.totalFees = totalFees;
	}




	public JSONObject getFees() {
		return fees;
	}




	public void setFees(JSONObject fees) {
		this.fees = fees;
	}




	public Timestamp getFeesAddedOn() {
		return feesAddedOn;
	}




	public void setFeesAddedOn(Timestamp feesAddedOn) {
		this.feesAddedOn = feesAddedOn;
	}




	public Timestamp getAccountCreatedOn() {
		return accountCreatedOn;
	}




	public void setAccountCreatedOn(Timestamp accountCreatedOn) {
		this.accountCreatedOn = accountCreatedOn;
	}




	public Timestamp getFeesDueDate() {
		return feesDueDate;
	}




	public void setFeesDueDate(Timestamp feesDueDate) {
		this.feesDueDate = feesDueDate;
	}




	public String getFeesDescription() {
		return feesDescription;
	}




	public void setFeesDescription(String feesDescription) {
		this.feesDescription = feesDescription;
	}




	public short getFeesCleared() {
		return feesCleared;
	}




	public void setFeesCleared(short feesCleared) {
		this.feesCleared = feesCleared;
	}




	public String getDeviceSerial() {
		return deviceSerial;
	}




	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}




	public short getActiveAccount() {
		return activeAccount;
	}




	public void setActiveAccount(short activeAccount) {
		this.activeAccount = activeAccount;
	}




	public String getAccountId() {
		return accountId;
	}




	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}




	@Override
	public String toString() {
		return "AccountFinancialInformation [meterNumber=" + meterNumber + ", firstName=" + firstName + ", lastName="
				+ lastName + ", otherNames=" + otherNames + ", balanceDue=" + balanceDue + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", isTransactional=" + isTransactional + ", totalFees=" + totalFees
				+ ", fees=" + fees + ", feesAddedOn=" + feesAddedOn + ", accountCreatedOn=" + accountCreatedOn
				+ ", feesDueDate=" + feesDueDate + ", feesDescription=" + feesDescription + ", feesCleared="
				+ feesCleared + ", deviceSerial=" + deviceSerial + ", activeAccount=" + activeAccount + ", accountId="
				+ accountId + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
	

}
