package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fredastone.pandacore.constants.PaymentChannel;

import lombok.Data;

/**
 * TLeasePayments generated by hbm2java
 */
@Entity
@Data
@Table(name = "t_lease_payment",schema="panda_core")
public class LeasePayment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id

	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;
	
	@Column(name = "leaseid", nullable = false, length = 36)
	private String leaseid;
	
	@Column(name = "amount", nullable = false, precision = 131089, scale = 0)
	private float amount;
	
	@Column(name = "name", length = 100)
	private String payeename;
	
	@Column(name = "msisdn", length = 20)
	private String payeemobilenumber;
	
	@Column(name = "channel", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private PaymentChannel paymentchannel;
	
	@Column(name = "status", nullable = false)
	private short paymentstatus;
	
	@Column(name = "transactionid", nullable = false, length = 36)
	private String transactionid;
	
	@Column(name = "channeltransactionid", length = 36)
	private String channeltransactionid;
	
	@Column(name = "channelstatuscode", length = 5)
	private String channelstatuscode;
	
	@Column(name = "channelmessage")
	private String channelmessage;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon",insertable=false, nullable = false, length = 29)
	private Date createdon;


}
