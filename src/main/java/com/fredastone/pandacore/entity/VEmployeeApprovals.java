package com.fredastone.pandacore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="v_employee_approvals",schema="panda_core")
public class VEmployeeApprovals {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="approvalid",updatable=false,insertable=false)
	private String approvalId;
	
	@Column(name="approvaltype")
	private int approvalType;
	
	@Column(name="approverrole")
	private String approvalRole;
	
	@Column(name="externalreference")
	private String externalReference;
	
	@Column(name="approvalorder")
	private int approvalOrder;
	
	@Column(name="status")
	private int status;
	

}
