package com.fredastone.pandacore.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fredastone.pandacore.entity.User;

import lombok.Data;

@Data
public class RestAgent {

	
	@Column(name = "firstname", nullable = false, length = 20)
	private String firstname;
	

	@Column(name = "middlename", length = 20)
	private String middlename;
	

	@Column(name = "lastname",nullable= false, length = 20)
	private String lastname;
	
	@Column(name = "personalemail", nullable = false)
	private String personalemail;
	
	
	@Column(name = "mobile", length = 20)
	private String mobile;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dateofbirth", length = 13)
	private Date dateofbirth;
	
	@Column(name = "agentcommissionrate", nullable = false, precision = 131089, scale = 0)
	private BigDecimal agentcommissionrate;

}
