package com.fredastone.pandacore.pandacore.models;

public class PaymentCompletedResponse {

	private float newBalance;
	private String meterNumber;
	private String message;
	private String transactionId;
	private String scheduledTransId;
	private String paymentToken;
	
	
	public float getNewBalance() {
		return newBalance;
	}
	public void setNewBalance(float newBalance) {
		this.newBalance = newBalance;
	}
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getScheduledTransId() {
		return scheduledTransId;
	}
	public void setScheduledTransId(String scheduledTransId) {
		this.scheduledTransId = scheduledTransId;
	}
	public String getPaymentToken() {
		return paymentToken;
	}
	public void setPaymentToken(String paymentToken) {
		this.paymentToken = paymentToken;
	}

	
	
}
