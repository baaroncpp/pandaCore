package com.fredastone.pandacore.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


@Data
@Entity
@Table(name = "v_sale_approval_review",schema="panda_core")
public class VSaleApprovalReview {

	@Id
	private String id;
	private String itemid;
	private String review;
	private String saleid;
	private String agentid;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
}
