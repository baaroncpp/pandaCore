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
 * TDistrict generated by hbm2java
 */
@Data
@Entity
@Table(name = "t_district",schema="panda_core")
public class District implements java.io.Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="panda_core.s_district",sequenceName="panda_core.s_district",allocationSize=1)
	@GeneratedValue(generator="panda_core.s_district")
	@Column(name = "id", unique = true, nullable = false,updatable=false)
	private int id;
	
	@Column(name = "name", length = 50)
	private String name;
	
	@Column(name = "regionid",  nullable = false)
	private int regionid;
	
	@Column(name = "inreview",  nullable = false)
	private boolean review;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRegionid() {
		return regionid;
	}

	public void setRegionid(int regionid) {
		this.regionid = regionid;
	}

	public boolean isReview() {
		return review;
	}

	public void setReview(boolean review) {
		this.review = review;
	}
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "districtid")
//	private List<County> counties;


}
