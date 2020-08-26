package com.fredastone.pandacore.models;

import java.util.Date;
import lombok.Data;

@Data
public class TokenRevenue {	
	private Date date;
	private String deviceSerial;
	private String token;
	private float amount;
}
