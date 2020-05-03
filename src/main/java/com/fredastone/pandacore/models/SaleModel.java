package com.fredastone.pandacore.models;

import java.util.Date;

import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.Product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter
public class SaleModel {
	
	private String id;
	private String saletype;
	private float amount;
	private String description;
	private String scannedserial;
	private float agentcommission;
	private AgentMeta agent;
	private float long_;
	private float lat;
	private boolean isreviewed;
	private short salestatus;
	private Date createdon;
	private Date completedon;
	private Product product;
	private int quantity;
	private CustomerMeta customer;


}
