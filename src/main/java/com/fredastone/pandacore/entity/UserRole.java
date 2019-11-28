package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 * TUserRoles generated by hbm2java 
 */
@Data
@Entity
@Table(name = "t_user_roles",schema="panda_core")
public class UserRole implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid", nullable = false)
	private Role role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", nullable = false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdon", nullable = false, length = 29)
	private Date createdon;
	
	@Column(name = "isactive", nullable = false)
	private Boolean isActive;
	/*
	public UserRole(Role role, User user, Date createdon, boolean isActive) {
		super();
		this.role = role;
		this.user = user;
		this.createdon = createdon;
		this.isActive = isActive;
	}*/

}
