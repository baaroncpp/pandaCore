package com.fredastone.pandacore.models;

import com.fredastone.pandacore.constants.PaymentChannel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyToken {

	private String deviceserial;
	private String msisdn;
	private PaymentChannel channel;
	private float amount;
	private String payeename;
	private int paymentchannel;
	private int paymentstatus;
	private String transactionId;
	private String channeltransactionid;
	private String channelstatuscode;
	private String channelmessage;
		
}
