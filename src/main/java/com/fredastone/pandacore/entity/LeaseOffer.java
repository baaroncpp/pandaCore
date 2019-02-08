package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TLeaseOffer generated by hbm2java
 */
@Entity
@Table(name = "t_lease_offer")
public class LeaseOffer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private Products TProducts;
	private String code;
	private short leaseperiod;
	private short percentlease;
	private BigDecimal recurrentpaymentamount;
	private boolean isactive;
	private BigDecimal intialdeposit;
	private String name;
	private String description;
	private Date createdon;

	public LeaseOffer() {
	}

	public LeaseOffer(int id, Products TProducts, String code, short leaseperiod, short percentlease,
			BigDecimal recurrentpaymentamount, boolean isactive, BigDecimal intialdeposit, String name,
			String description) {
		this.id = id;
		this.TProducts = TProducts;
		this.code = code;
		this.leaseperiod = leaseperiod;
		this.percentlease = percentlease;
		this.recurrentpaymentamount = recurrentpaymentamount;
		this.isactive = isactive;
		this.intialdeposit = intialdeposit;
		this.name = name;
		this.description = description;
	}

	public LeaseOffer(int id, Products TProducts, String code, short leaseperiod, short percentlease,
			BigDecimal recurrentpaymentamount, boolean isactive, BigDecimal intialdeposit, String name,
			String description, Date createdon) {
		this.id = id;
		this.TProducts = TProducts;
		this.code = code;
		this.leaseperiod = leaseperiod;
		this.percentlease = percentlease;
		this.recurrentpaymentamount = recurrentpaymentamount;
		this.isactive = isactive;
		this.intialdeposit = intialdeposit;
		this.name = name;
		this.description = description;
		this.createdon = createdon;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productid", nullable = false)
	public Products getTProducts() {
		return this.TProducts;
	}

	public void setTProducts(Products TProducts) {
		this.TProducts = TProducts;
	}

	@Column(name = "code", nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "leaseperiod", nullable = false)
	public short getLeaseperiod() {
		return this.leaseperiod;
	}

	public void setLeaseperiod(short leaseperiod) {
		this.leaseperiod = leaseperiod;
	}

	@Column(name = "percentlease", nullable = false)
	public short getPercentlease() {
		return this.percentlease;
	}

	public void setPercentlease(short percentlease) {
		this.percentlease = percentlease;
	}

	@Column(name = "recurrentpaymentamount", nullable = false, precision = 131089, scale = 0)
	public BigDecimal getRecurrentpaymentamount() {
		return this.recurrentpaymentamount;
	}

	public void setRecurrentpaymentamount(BigDecimal recurrentpaymentamount) {
		this.recurrentpaymentamount = recurrentpaymentamount;
	}

	@Column(name = "isactive", nullable = false)
	public boolean isIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	@Column(name = "intialdeposit", nullable = false, precision = 131089, scale = 0)
	public BigDecimal getIntialdeposit() {
		return this.intialdeposit;
	}

	public void setIntialdeposit(BigDecimal intialdeposit) {
		this.intialdeposit = intialdeposit;
	}

	@Column(name = "name", nullable = false, length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon", length = 29)
	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

}
