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
 * TLease generated by hbm2java
 */
@Entity
@Table(name = "t_lease")
public class Lease implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Customer TCustomer;
	private Employees TEmployees;
	private Products TProducts;
	private BigDecimal initialdeposit;
	private BigDecimal couponvalue;
	private Date dateinstalled;
	private Date createdon;

	public Lease() {
	}

	public Lease(String id, Customer TCustomer, Products TProducts, BigDecimal initialdeposit,
			BigDecimal couponvalue, Date createdon) {
		this.id = id;
		this.TCustomer = TCustomer;
		this.TProducts = TProducts;
		this.initialdeposit = initialdeposit;
		this.couponvalue = couponvalue;
		this.createdon = createdon;
	}

	public Lease(String id, Customer TCustomer, Employees TEmployees, Products TProducts, BigDecimal initialdeposit,
			BigDecimal couponvalue, Date dateinstalled, Date createdon) {
		this.id = id;
		this.TCustomer = TCustomer;
		this.TEmployees = TEmployees;
		this.TProducts = TProducts;
		this.initialdeposit = initialdeposit;
		this.couponvalue = couponvalue;
		this.dateinstalled = dateinstalled;
		this.createdon = createdon;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerid", nullable = false)
	public Customer getTCustomer() {
		return this.TCustomer;
	}

	public void setTCustomer(Customer TCustomer) {
		this.TCustomer = TCustomer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saleagentid")
	public Employees getTEmployees() {
		return this.TEmployees;
	}

	public void setTEmployees(Employees TEmployees) {
		this.TEmployees = TEmployees;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productid", nullable = false)
	public Products getTProducts() {
		return this.TProducts;
	}

	public void setTProducts(Products TProducts) {
		this.TProducts = TProducts;
	}

	@Column(name = "initialdeposit", nullable = false, precision = 131089, scale = 0)
	public BigDecimal getInitialdeposit() {
		return this.initialdeposit;
	}

	public void setInitialdeposit(BigDecimal initialdeposit) {
		this.initialdeposit = initialdeposit;
	}

	@Column(name = "couponvalue", nullable = false, precision = 131089, scale = 0)
	public BigDecimal getCouponvalue() {
		return this.couponvalue;
	}

	public void setCouponvalue(BigDecimal couponvalue) {
		this.couponvalue = couponvalue;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateinstalled", length = 29)
	public Date getDateinstalled() {
		return this.dateinstalled;
	}

	public void setDateinstalled(Date dateinstalled) {
		this.dateinstalled = dateinstalled;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon", nullable = false, length = 29)
	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

}
