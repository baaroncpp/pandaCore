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
 * TOpex generated by hbm2java
 */
@Entity
@Table(name = "t_opex")
public class Opex implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Employees TEmployees;
	private OpexType TOpexType;
	private BigDecimal amount;
	private String description;
	private Date createdon;
	private Date approvedon;

	public Opex() {
	}

	public Opex(String id, Employees TEmployees, OpexType TOpexType, BigDecimal amount, Date createdon) {
		this.id = id;
		this.TEmployees = TEmployees;
		this.TOpexType = TOpexType;
		this.amount = amount;
		this.createdon = createdon;
	}

	public Opex(String id, Employees TEmployees, OpexType TOpexType, BigDecimal amount, String description,
			Date createdon, Date approvedon) {
		this.id = id;
		this.TEmployees = TEmployees;
		this.TOpexType = TOpexType;
		this.amount = amount;
		this.description = description;
		this.createdon = createdon;
		this.approvedon = approvedon;
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
	@JoinColumn(name = "employeeid", nullable = false)
	public Employees getTEmployees() {
		return this.TEmployees;
	}

	public void setTEmployees(Employees TEmployees) {
		this.TEmployees = TEmployees;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expensetype", nullable = false)
	public OpexType getTOpexType() {
		return this.TOpexType;
	}

	public void setTOpexType(OpexType TOpexType) {
		this.TOpexType = TOpexType;
	}

	@Column(name = "amount", nullable = false, precision = 131089, scale = 0)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon", nullable = false, length = 29)
	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approvedon", length = 29)
	public Date getApprovedon() {
		return this.approvedon;
	}

	public void setApprovedon(Date approvedon) {
		this.approvedon = approvedon;
	}

}
