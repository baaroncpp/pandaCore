package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * TPaymentChannel generated by hbm2java
 */
@Data
@Entity
@Table(name = "t_payment_channel",schema="panda_core")
public class PaymentChannel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id

	@Column(name = "id", unique = true, nullable = false)
	private short id;

	@Column(name = "channelname", nullable = false, length = 20)
	private String channelname;
	
	@Column(name = "channelcode", nullable = false, length = 5)
	private String channelcode;
	
	@Column(name = "isenabled")
	private Boolean isenabled;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon", nullable = false, length = 29)
	private Date createdon;


}
