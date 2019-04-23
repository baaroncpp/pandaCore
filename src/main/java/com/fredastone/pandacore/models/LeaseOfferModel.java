package com.fredastone.pandacore.models;

import lombok.Data;

@Data
public class LeaseOfferModel {

	private int id;
	private String code;
	private String productid;
	private short leaseperiod;
	private short percentlease;
	private double recurrentpaymentamount;
	private double initialdeposit;
	private String name;
	private String description;
	private boolean isactive;
}
