package com.fredastone.pandacore.entity;
// Generated Feb 7, 2019 1:36:16 AM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

/**
 * TParishes generated by hbm2java
 */
@Data
@Builder
@Entity
@Table(name = "t_parish",schema="panda_core")
public class Parish implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="panda_core.s_subcounty",sequenceName="panda_core.s_subcounty",allocationSize=1)
	@GeneratedValue(generator="panda_core.s_subcounty")
	@Column(name = "id", unique = true, nullable = false,updatable=false)
	private int id;
	
	@Column(name = "subcountyid", nullable = false)
	private int subcountyid;
	
	@Column(name = "name", length = 50)
	private String name;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parishid")
//	private Set<Village> villages = new HashSet<Village>(0);
//	

}
