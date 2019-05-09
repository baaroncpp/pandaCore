package com.fredastone.pandacore.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fredastone.pandacore.models.RegionModel;
import com.fredastone.pandacore.repository.RegionRepositoryCustom;

public class RegionRepositoryImpl implements RegionRepositoryCustom {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static String SELECT_DISTRICT = "Select * from panda_core.t_district order by name desc";
	
	private static String SELECT_COUNTIES = "Select id,name from panda_core.t_county wHERE districtid = :districtid";
	
	private static String SELECT_SUBCOUNTIES = "SELECT k.id,k.name from panda_core.t_subcounty k where k.countyid = :countyid";
	
	private static String SELECT_PARISH =" SELECT k.id,k.name from panda_core.t_parish k where k.subcountyid  = :subcountyid";
	
	private static String SELECT_VILLAGE =" SELECT k.id,k.name from panda_core.t_village k where k.parishid = :parishid";

	@Override
	public List<RegionModel> getDistricts() {
	
		return jdbcTemplate.query(SELECT_DISTRICT, new RegionDataMapper());
	}

	@Override
	public List<RegionModel> getDistrictAndCounties(int districtId) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("districtid", districtId);
		
		return jdbcTemplate.query(SELECT_COUNTIES,map, new RegionDataMapper());
		
	}

	@Override
	public List<RegionModel> getCountyAndSubCounties(int countyId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("countyid", countyId);
		
		return jdbcTemplate.query(SELECT_COUNTIES,map, new RegionDataMapper());
	}

	@Override
	public List<RegionModel> getVillages(int parishid) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("parishid", parishid);
		
		return jdbcTemplate.query(SELECT_VILLAGE,map, new RegionDataMapper());
	}
	
	@Override
	public List<RegionModel> getSubCounty(int countid) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("countyid", countid);
		
		return jdbcTemplate.query(SELECT_SUBCOUNTIES,map, new RegionDataMapper());
	}
	
	@Override
	public List<RegionModel> getParish(int subcountyid) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("subcountyid", subcountyid);
		
		return jdbcTemplate.query(SELECT_PARISH,map, new RegionDataMapper());
	}
	
	


	private class RegionDataMapper implements RowMapper<RegionModel>{

		@Override
		public RegionModel mapRow(ResultSet rs, int arg1) throws SQLException {
			
			return RegionModel.builder().id(rs.getInt("id"))
					.name(rs.getString("name")).build();
		}
		
	}

	
}
