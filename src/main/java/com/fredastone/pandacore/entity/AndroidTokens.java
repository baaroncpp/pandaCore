package com.fredastone.pandacore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name="t_android_tokens",schema="panda_core")
@Data
public class AndroidTokens {
	
	@Id
	private String userid;

	private String token; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createdon",insertable=false,updatable=false)
	private Date createdon;
}
