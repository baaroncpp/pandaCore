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

import lombok.Data;

/**
 * TCapex generated by hbm2java
 */
@Data
@Entity
@Table(name = "t_capex",schema="panda_core")
public class Capex implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id

	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expensetype", nullable = false)
	private CapexType TCapexType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeid", nullable = false)
	private EmployeeMeta TEmployees;
	
	@Column(name = "amount", nullable = false, precision = 131089, scale = 0)
	private BigDecimal amount;
	
	@Column(name = "description")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon", nullable = false, length = 29)
	private Date createdon;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approvedon", length = 29)
	private Date approvedon;


}
