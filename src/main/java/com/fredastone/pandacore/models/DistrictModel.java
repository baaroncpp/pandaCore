package com.fredastone.pandacore.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictModel {

	private int districtid;
	private String districtname;
	private int countyid;
	private String countyname;

}
