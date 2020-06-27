package com.fredastone.pandacore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_sale_rollback_refund",schema="panda_core")
public class SaleRollBackRefund {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;
	
	@Column(name = "amount", nullable = false)
	private float amount;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salerollbackid", nullable = false)
	private SaleRollback saleRollback;
	
	@Column(name = "createdon")
	private Date createdon;
	
	@Column(name = "ispaid", nullable = false)
	private boolean ispaid;
	
	@Column(name = "repaidon", nullable = false)
	private Date repaidon;
}
