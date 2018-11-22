package com.fredastone.pandacore.pandacore.models;

public class RollbackResponse {

	private String status;
	private String oldBalance;
	private String oldToken;
	private String meterNumber;
	private String payingMsisdn;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOldBalance() {
		return oldBalance;
	}
	public void setOldBalance(String oldBalance) {
		this.oldBalance = oldBalance;
	}
	public String getOldToken() {
		return oldToken;
	}
	public void setOldToken(String oldToken) {
		this.oldToken = oldToken;
	}
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public String getPayingMsisdn() {
		return payingMsisdn;
	}
	public void setPayingMsisdn(String payingMsisdn) {
		this.payingMsisdn = payingMsisdn;
	}
	
	
}
