package com.fredastone.pandacore.models;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserModel {
	
	private String username;
	private String password;
	private String usertype;
	private String firstname;
	private String middlename;
	private String lastname;
	private String email;
	private String primaryphone;
	private String companyemail;
	private Date dateofbirth;
	
	List<Approvers> approvers;
	
}
