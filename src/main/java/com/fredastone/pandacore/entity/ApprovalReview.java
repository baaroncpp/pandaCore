package com.fredastone.pandacore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_approval_review",schema="panda_core")
public class ApprovalReview {
	
	
	@Id
	
	String id;
	
	@NotEmpty
	private String itemid;
	
	private int reviewtype;
	
	@NotEmpty
	private String review;
	
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createdon",updatable=false,insertable=false)
	private Date createdon;

}
