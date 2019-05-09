package com.fredastone.pandacore.repository;

import java.util.List;

import com.fredastone.pandacore.models.RegionModel;

public interface RegionRepositoryCustom {

	List<RegionModel> getDistricts();
	List<RegionModel> getDistrictAndCounties(int districtId);
	List<RegionModel> getCountyAndSubCounties(int countyId);
	List<RegionModel> getSubCounty(int countid);
	List<RegionModel> getParish(int subcountyid);
	List<RegionModel> getVillages(int parishid);
}

