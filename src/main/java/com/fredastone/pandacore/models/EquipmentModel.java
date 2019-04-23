package com.fredastone.pandacore.models;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class EquipmentModel {
	
	
	private String categoryId;

	private String name;
	
	private String model;
	
	private Date dateofmanufacture;
	
	private boolean available;
	
	private String description;
	
	private String serial;
	
	private BigDecimal quantity;

}
