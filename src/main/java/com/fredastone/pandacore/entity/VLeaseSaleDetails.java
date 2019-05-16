package com.fredastone.pandacore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="v_lease_sales_detail",schema="panda_core")
@Data
@NoArgsConstructor
public class VLeaseSaleDetails {

	@Id
	private String id;
	private String saletype;
	private float amount;
	private String description;
	@Column(name = "scannedserial")
	private String deviceserial;
	private float agentcommission;
	private String agentid;
	@Column(name="long_")
	private float salelongitude;
	@Column(name="lat_")
	private float salelatitude;
	private int salestatus;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon")
	private Date saledate;
	private String productid;
	private int quantity;
	private String customerid;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "completedon")
	private Date salecompletiondate;
	private boolean isreviewed;
	private String companyname;
	private String productname;
	private String productcode;
	private String productdescription;
	private float dailypayment;
	@Column(name="totalleaseperiod")
	private int leaseperiod;
	@Column(name="totalleasevalue")
	private float leasevalue;
	private String agenttype;
	private int agentcommissionrate;
	private String district;
	private String county;
	private String subcounty;
	private String parish;
	private String village;
	private int villageid;
}
