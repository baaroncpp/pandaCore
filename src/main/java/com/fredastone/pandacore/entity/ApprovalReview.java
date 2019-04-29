package com.fredastone.pandacore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name="t_approval_review",schema="panda_core")
public class ApprovalReview {
	
	
	@Id
	
	String id;
	
	@NotEmpty
	private String approvalid;
	
	@NotEmpty
	private String review;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createdon",updatable=false,insertable=false)
	private Date createdon;

}
