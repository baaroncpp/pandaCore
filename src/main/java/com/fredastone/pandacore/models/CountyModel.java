package com.fredastone.pandacore.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountyModel {
	
	private int countid;
	private String countyname;
	private int subcountid;
	private String subcountyname;

}
