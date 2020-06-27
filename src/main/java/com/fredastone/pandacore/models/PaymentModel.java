package com.fredastone.pandacore.models;

import lombok.Data;
import java.util.Date;

@Data
public class PaymentModel {
	
	String number;
	String customername;
	String msisdn;
	Double amount;
	Date date;
	String serial;
	String agent;

}
