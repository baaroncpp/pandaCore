package com.fredastone.pandacore.models;

import java.util.Date;

import com.fredastone.pandacore.constants.IdType;
import com.fredastone.pandacore.constants.Sex;
import com.fredastone.pandacore.constants.Title;
import com.fredastone.pandacore.constants.UserType;

import lombok.Data;

@Data
public class CustomerModel {
	
	private String companyemail;
	private Date dateofbirth;
	private String email;
	private String firstname;
	private String lastname;
	private String middlename;
	private String password;
	private String primaryphone;
	private String username;
	private UserType usertype;
	private String sex;
	private String title;
	
	private String address;
	private String consentformpath;
	private float homelat;
	private float homelong;
	private String idcopypath;
	private String idnumber;
	private IdType idtype;
	private String profilephotopath;
	private String secondaryemail;
	private String secondaryphone;
	private short villageid;

}
