package com.fredastone.pandacore.pandacore.entity;

import java.sql.Timestamp;

public class Devices {
	
   private String id;
   private String make;
   private String model;
   private String device_serial;
   private int yof;
   private  Timestamp created_on;
   private int is_active;
	   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDevice_serial() {
		return device_serial;
	}
	public void setDevice_serial(String device_serial) {
		this.device_serial = device_serial;
	}
	public int getYof() {
		return yof;
	}
	public void setYof(int yof) {
		this.yof = yof;
	}
	public Timestamp getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Timestamp created_on) {
		this.created_on = created_on;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	@Override
	public String toString() {
		return "Devices [id=" + id + ", make=" + make + ", model=" + model + ", device_serial=" + device_serial
				+ ", yof=" + yof + ", created_on=" + created_on + ", is_active=" + is_active + "]";
	}
	   

}
