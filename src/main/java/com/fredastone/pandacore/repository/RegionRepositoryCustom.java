package com.fredastone.pandacore.repository;

import java.util.List;

import com.fredastone.pandacore.entity.District;
import com.fredastone.pandacore.entity.Parish;
import com.fredastone.pandacore.entity.Subcounty;
import com.fredastone.pandacore.models.CountyModel;
import com.fredastone.pandacore.models.DistrictModel;
import com.fredastone.pandacore.models.VillageModel;

public interface RegionRepositoryCustom {

	List<District> getDistricts();
	List<DistrictModel> getDistrictAndCounties(int districtId);
	List<CountyModel> getCountyAndSubCounties(int countyId);
	List<Subcounty> getSubCounty(int countid);
	List<Parish> getParish(int subcountyid);
	List<VillageModel> getVillages(int parishid);
}

