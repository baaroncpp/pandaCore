package com.fredastone.pandacore.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class LoginUser {
	
	private String id;
	private String username;
	private String email;
	private String password;
	private boolean isapproved;
	private boolean isactive;
	private String rolename;
	private Date passwordreseton;
	private  List<Role> roles;

}
