package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
/**
 * TSales generated by hbm2java
 */
@Data
@Entity
@Table(name = "t_sale",schema="panda_core")
public class Sale implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String saletype;
	private float amount;
	private String description;
	private String scannedserial;
	private float agentcommission;
	private String agentid;
	private float long_;
	private float lat;
	private boolean isreviewed;
	private short salestatus;
	private Date createdon;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "completedon",nullable = true, length = 29)
	private Date completedon;
	
	@Column(name = "productid",nullable=true)
	private String productid;

	@Column(name = "quantity",nullable=false)
	private int quantity;
	
	@Column(name = "customerid",nullable=false)
	private String customerid;

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "saletype")
	public String getSaletype() {
		return this.saletype;
	}

	public void setSaletype(String saletype) {
		this.saletype = saletype;
	}

	@Column(name = "amount", nullable = false, precision = 131089, scale = 0)
	public float getAmount() {
		return this.amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "scannedserial", length = 50)
	public String getScannedserial() {
		return this.scannedserial;
	}

	public void setScannedserial(String scannedserial) {
		this.scannedserial = scannedserial;
	}

	@Column(name = "agentcommission", nullable = false, precision = 131089, scale = 0)
	public float getAgentcommission() {
		return this.agentcommission;
	}

	public void setAgentcommission(float agentcommission) {
		this.agentcommission = agentcommission;
	}

	@Column(name = "agentid", nullable = false, length = 36)
	public String getAgentid() {
		return this.agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	@Column(name = "long_", nullable = false, precision = 131089, scale = 0)
	public float getLong_() {
		return this.long_;
	}

	public void setLong_(float long_) {
		this.long_ = long_;
	}

	@Column(name = "lat_", nullable = false, precision = 131089, scale = 0)
	public float getLat() {
		return this.lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	@Column(name = "salestatus", nullable = false)
	public short getSalestatus() {
		return this.salestatus;
	}

	public void setSalestatus(short salestatus) {
		this.salestatus = salestatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon", insertable=false,updatable=false,nullable = false, length = 29)
	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}


}
