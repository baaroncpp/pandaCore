package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TRegionUg generated by hbm2java
 */
@Entity
@Table(name = "t_region_ug")
public class RegionUg implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;

	public RegionUg() {
	}

	public RegionUg(int id) {
		this.id = id;
	}

	public RegionUg(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", length = 10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
