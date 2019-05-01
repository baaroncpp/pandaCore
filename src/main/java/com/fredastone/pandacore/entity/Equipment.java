package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import lombok.Data;

/**
 * TEquipment generated by hbm2java
 */
@Data
@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "getEquipmentBySerial",query = "Select a.* from Equipment a",resultClass = Equipment.class)
})
@Table(name = "t_equipment",schema="panda_core")
public class Equipment implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id

	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryid", nullable = false)
	private EquipCategory category;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "model", nullable = false)
	private String model;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateofmanufacture", length = 29)
	private Date dateofmanufacture;
	
	@Column(name = "available", nullable = false)
	private boolean available;
	

	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "serial", nullable = false,unique = true)
	private String serial;
	
	
	@Column(name = "quantity", nullable = false, precision = 131089, scale = 0)
	private BigDecimal quantity;
	
	@Column(name = "thumbnail", nullable = true)
	String equipmentPhoto;

	@Basic(optional=false)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon",insertable=false,updatable=false, nullable = false, length = 29)
	private Date createdon;

}