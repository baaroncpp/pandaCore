package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TCounty generated by hbm2java
 */
@Entity
@Table(name = "t_county")
public class County implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private District TDistrict;
	private String name;
	private Set<Subcounty> TSubcounties = new HashSet<Subcounty>(0);

	public County() {
	}

	public County(int id, District TDistrict) {
		this.id = id;
		this.TDistrict = TDistrict;
	}

	public County(int id, District TDistrict, String name, Set<Subcounty> TSubcounties) {
		this.id = id;
		this.TDistrict = TDistrict;
		this.name = name;
		this.TSubcounties = TSubcounties;
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
	@JoinColumn(name = "districtid", nullable = false)
	public District getTDistrict() {
		return this.TDistrict;
	}

	public void setTDistrict(District TDistrict) {
		this.TDistrict = TDistrict;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TCounty")
	public Set<Subcounty> getTSubcounties() {
		return this.TSubcounties;
	}

	public void setTSubcounties(Set<Subcounty> TSubcounties) {
		this.TSubcounties = TSubcounties;
	}

}
