package com.fredastone.pandacore.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Table(name="v_verification_data",schema="panda_core")
@Entity
@Data
public class VerificationInfo {

	@Id

	String id;
	private String firstname;
	private String lastname;
	private String middlename;
	private String primaryphone;
	private String scannedserial;
	private float amount;
	private int salestatus;
	private String address;
	private String agentid;
	private boolean isreviewed;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
}
