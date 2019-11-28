package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * TCounty generated by hbm2java
 */
@Data
@Entity
@Table(name = "t_county",schema="panda_core")
public class County implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@SequenceGenerator(name="panda_core.s_county",sequenceName="panda_core.s_county",allocationSize=1)
	@GeneratedValue(generator="panda_core.s_county")
	@Column(name = "id", unique = true, nullable = false,updatable=false)
	private int id;
	
	@Column(name="districtid",nullable=false)
	private int districtid;
	
	@Column(name = "name", length = 50)
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDistrictid() {
		return districtid;
	}

	public void setDistrictid(int districtid) {
		this.districtid = districtid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "countyid")
//	private Set<Subcounty> subcounties = new HashSet<Subcounty>(0);

	
}
