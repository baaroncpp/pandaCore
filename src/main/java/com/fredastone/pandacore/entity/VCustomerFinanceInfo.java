package com.fredastone.pandacore.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="v_customer_finance_info",schema="panda_core")
public class VCustomerFinanceInfo {


	private String id;
	private String saleid;
	private String leaseid;
	@Id
	private String deviceserial;
	
	private String firstname;
	private String lastname;
	private String middlename;
	private float dailypayment;
	private int totalleaseperiod;
	

}
