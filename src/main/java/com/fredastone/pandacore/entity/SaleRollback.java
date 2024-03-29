package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
/**
 * TSaleRollback generated by hbm2java
 */
@Data
@Entity
@Table(name = "t_sale_rollback",schema="panda_core")
public class SaleRollback implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5724622961646396961L;
	private String id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Sale sale;
	private String description;
	private Date createdon;

	public SaleRollback() {
	}

	public SaleRollback(String id, Sale sale, Date createdon) {
		this.id = id;
		this.sale = sale;
		this.createdon = createdon;
	}

	public SaleRollback(String id, Sale sale, String description, Date createdon) {
		this.id = id;
		this.sale = sale;
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
	public Sale getSale() {
		return this.sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
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
