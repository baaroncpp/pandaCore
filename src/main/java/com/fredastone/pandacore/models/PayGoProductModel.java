package com.fredastone.pandacore.models;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter
public class PayGoProductModel {
	private String tokenSerialNumber;	
	private int leaseOfferid;
	private PayGoProductStatus payGoProductStatus;
}
