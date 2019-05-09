package com.fredastone.pandacore.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fredastone.pandacore.entity.District;
import com.fredastone.pandacore.entity.Parish;
import com.fredastone.pandacore.entity.Subcounty;
import com.fredastone.pandacore.models.CountyModel;
import com.fredastone.pandacore.models.DistrictModel;
import com.fredastone.pandacore.models.VillageModel;

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
	public List<District> getDistricts() {
	
		return jdbcTemplate.query(SELECT_DISTRICT, new DistrictMapper());
	}

	@Override
	public List<DistrictModel> getDistrictAndCounties(int districtId) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("districtid", districtId);
		
		return jdbcTemplate.query(SELECT_COUNTIES,map, new DistrictCountyMapper());
		
	}

	@Override
	public List<CountyModel> getCountyAndSubCounties(int countyId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("countyid", countyId);
		
		return jdbcTemplate.query(SELECT_COUNTIES,map, new CountySubCountyMapper());
	}

	@Override
	public List<VillageModel> getVillages(int parishid) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("parishid", parishid);
		
		return jdbcTemplate.query(SELECT_VILLAGE,map, new VillageMapper());
	}
	
	@Override
	public List<Subcounty> getSubCounty(int countid) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("countyid", countid);
		
		return jdbcTemplate.query(SELECT_SUBCOUNTIES,map, new SubCountyMapper());
	}
	
	@Override
	public List<Parish> getParish(int subcountyid) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("subcountyid", subcountyid);
		
		return jdbcTemplate.query(SELECT_PARISH,map, new ParishMapper());
	}
	
	
	private class DistrictMapper implements RowMapper<District>{

		@Override
		public District mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			District d  = new District();
			d.setId(rs.getInt("id"));
			d.setName(rs.getString("name"));
			d.setReview(rs.getBoolean("inreview"));
			return d;
		}
		
	}
	
	private class DistrictCountyMapper implements RowMapper<DistrictModel>{

		@Override
		public DistrictModel mapRow(ResultSet rs, int arg1) throws SQLException {
			return DistrictModel.builder()
			.countyid(rs.getInt("id"))
			.countyname(rs.getString("name"))
			.build();

		}
		
	}
	
	private class CountySubCountyMapper implements RowMapper<CountyModel>{

		@Override
		public CountyModel mapRow(ResultSet rs, int arg1) throws SQLException {
			
			return CountyModel.builder().countid(rs.getInt("county_id"))
			.countyname(rs.getString("countyname"))
			.subcountid(rs.getInt("id"))
			.subcountyname(rs.getString("name"))
			.build();
		}
		
	}
	
	private class SubCountyMapper implements RowMapper<Subcounty>{

		@Override
		public Subcounty mapRow(ResultSet rs, int arg1) throws SQLException {
			
			return Subcounty.builder().id(rs.getInt("id"))
					.name(rs.getString("name")).build();
		}
		
	}

	private class VillageMapper implements RowMapper<VillageModel>{

		@Override
		public VillageModel mapRow(ResultSet rs, int arg1) throws SQLException {
			
			return VillageModel.builder().id(rs.getInt("id"))
					.name(rs.getString("name")).build();
		}
		
	}

	private class ParishMapper implements RowMapper<Parish>{

		@Override
		public Parish mapRow(ResultSet rs, int arg1) throws SQLException {
			
			return Parish.builder().id(rs.getInt("id"))
					.name(rs.getString("name")).build();
		}
		
	}

	
}
