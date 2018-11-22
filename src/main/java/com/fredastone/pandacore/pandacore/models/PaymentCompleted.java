package com.fredastone.pandacore.pandacore.models;

public class PaymentCompleted {
	
	private String meterNumber;
	private float amount;
	private String transactionId;
	private String scheduledTransactionId;
	private String paymingMsisdn;
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getScheduledTransactionId() {
		return scheduledTransactionId;
	}
	public void setScheduledTransactionId(String scheduledTransactionId) {
		this.scheduledTransactionId = scheduledTransactionId;
	}
	public String getPaymingMsisdn() {
		return paymingMsisdn;
	}
	public void setPaymingMsisdn(String paymingMsisdn) {
		this.paymingMsisdn = paymingMsisdn;
	}
	@Override
	public String toString() {
		return "PaymentCompleted [meterNumber=" + meterNumber + ", amount=" + amount + ", transactionId="
				+ transactionId + ", scheduledTransactionId=" + scheduledTransactionId + ", paymingMsisdn="
				+ paymingMsisdn + "]";
	}

	
	

}
