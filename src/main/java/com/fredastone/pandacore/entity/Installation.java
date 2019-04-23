package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.io.Serializable;
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
 * TInstallation generated by hbm2java
 */

@Data
@Entity
@Table(name = "t_installation")
public class Installation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7014692064098319088L;
	private String id;
	private EmployeeMeta employeeMetaBySaleagentid;
	private EmployeeMeta employeeMetaByCustomerid;
	private Serializable coordinates;
	private Date createdon;
	private Date endtime;

	public Installation() {
	}

	public Installation(String id, EmployeeMeta employeeMetaBySaleagentid, EmployeeMeta employeeMetaByCustomerid,
			Serializable coordinates, Date createdon, Date endtime) {
		this.id = id;
		this.employeeMetaBySaleagentid = employeeMetaBySaleagentid;
		this.employeeMetaByCustomerid = employeeMetaByCustomerid;
		this.coordinates = coordinates;
		this.createdon = createdon;
		this.endtime = endtime;
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
	@JoinColumn(name = "saleagentid", nullable = false)
	public EmployeeMeta getEmployeeMetaBySaleagentid() {
		return this.employeeMetaBySaleagentid;
	}

	public void setEmployeeMetaBySaleagentid(EmployeeMeta employeeMetaBySaleagentid) {
		this.employeeMetaBySaleagentid = employeeMetaBySaleagentid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerid", nullable = false)
	public EmployeeMeta getEmployeeMetaByCustomerid() {
		return this.employeeMetaByCustomerid;
	}

	public void setEmployeeMetaByCustomerid(EmployeeMeta employeeMetaByCustomerid) {
		this.employeeMetaByCustomerid = employeeMetaByCustomerid;
	}

	@Column(name = "coordinates", nullable = false)
	public Serializable getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(Serializable coordinates) {
		this.coordinates = coordinates;
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
	@Column(name = "endtime", nullable = false, length = 29)
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
}
