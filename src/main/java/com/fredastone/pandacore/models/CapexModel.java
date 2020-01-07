package com.fredastone.pandacore.models;

import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CapexModel {
	
	String id;
	BigDecimal amount;
	String description;
	String employeeid;
	short capexTypeid;

}
