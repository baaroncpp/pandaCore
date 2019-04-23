package com.fredastone.pandacore.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "t_approver",schema = "panda_core", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "approvalservice", "approvalorder" }),
		@UniqueConstraint(columnNames = { "approvalservice", "userid" }) })
public class Approver implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private User user;
	
	@Column(name = "approvalservice", nullable = false, length = 10)
	private String approvalservice;
	
	private int approvalorder;
	private Boolean isenabled;
	private Date createdon;

	public Approver() {
	}

	public Approver(String id, User user, String approvalservice, int approvalorder, Date createdon) {
		this.id = id;
		this.user = user;
		this.approvalservice = approvalservice;
		this.approvalorder = approvalorder;
		this.createdon = createdon;
	}

	public Approver(String id, User user, String approvalservice, int approvalorder, Boolean isenabled,
			Date createdon) {
		this.id = id;
		this.user = user;
		this.approvalservice = approvalservice;
		this.approvalorder = approvalorder;
		this.isenabled = isenabled;
		this.createdon = createdon;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setTUser(User user) {
		this.user = user;
	}


	@Column(name = "approvalorder", nullable = false)
	public int getApprovalorder() {
		return this.approvalorder;
	}

	public void setApprovalorder(int approvalorder) {
		this.approvalorder = approvalorder;
	}

	@Column(name = "isenabled")
	public Boolean getIsenabled() {
		return this.isenabled;
	}

	public void setIsenabled(Boolean isenabled) {
		this.isenabled = isenabled;
	}

	@Basic(optional=false)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon",insertable=false,updatable=false, nullable = false, length = 29)
	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

}

