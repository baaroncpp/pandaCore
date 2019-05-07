package com.fredastone.pandacore.repository;

import java.util.List;

import com.fredastone.pandacore.entity.County;
import com.fredastone.pandacore.entity.District;
import com.fredastone.pandacore.entity.Region;
import com.fredastone.pandacore.entity.Subcounty;
import com.fredastone.pandacore.entity.Village;

public interface RegionRepositoryCustom {

	List<Region> getRegion();
	List<District> getDistrict(int regionId);
	List<County> getCounty(int districtId);
	List<Subcounty> getSubCounty(int county);
	List<Village> getVillage(int parishId);
}

