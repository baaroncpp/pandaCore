package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * TEquipCategory generated by hbm2java
 */
@Data
@Entity
@Table(name = "t_equip_category",schema="panda_core")
public class EquipCategory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", unique = true, nullable = false,updatable=false)
	private String id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Basic(optional=false)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createon",insertable=false,updatable=false, nullable = false, length = 29)
	private Date createon;
	
	@Column(name = "isactive")
	private Boolean isactive;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateon() {
		return createon;
	}

	public void setCreateon(Date createon) {
		this.createon = createon;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	
}
