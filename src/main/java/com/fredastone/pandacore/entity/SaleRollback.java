package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

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
 * TSaleRollback generated by hbm2java
 */
@Entity
@Table(name = "t_sale_rollback")
public class SaleRollback implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Sales TSales;
	private String description;
	private Date createdon;

	public SaleRollback() {
	}

	public SaleRollback(String id, Sales TSales, Date createdon) {
		this.id = id;
		this.TSales = TSales;
		this.createdon = createdon;
	}

	public SaleRollback(String id, Sales TSales, String description, Date createdon) {
		this.id = id;
		this.TSales = TSales;
		this.description = description;
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
	@JoinColumn(name = "saleid", nullable = false)
	public Sales getTSales() {
		return this.TSales;
	}

	public void setTSales(Sales TSales) {
		this.TSales = TSales;
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

}
