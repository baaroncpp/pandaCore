package com.fredastone.pandacore.models;

import java.math.BigDecimal; 
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpexModel {
	
	private String id;
	private String employeeId;
	private short opexTypeid;
	private BigDecimal amount;
	private String description;

}
