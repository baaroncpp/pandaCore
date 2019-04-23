package com.fredastone.pandacore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_lease_payments_extra",schema="panda_core")
public class LeasePaymentExtra {

	@Id
	private String id;
	private String leaseid;
	private float amount;
	private boolean isrefunded;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createdon",updatable=false,insertable=false,nullable=false)
	private Date createdon;
	
}
