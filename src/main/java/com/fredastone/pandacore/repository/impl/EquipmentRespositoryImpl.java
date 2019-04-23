package com.fredastone.pandacore.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.EquipCategory;
import com.fredastone.pandacore.entity.Equipment;
import com.fredastone.pandacore.repository.EquipmentRepositoryCustom;

@Repository
public class EquipmentRespositoryImpl implements EquipmentRepositoryCustom {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Equipment findEquipmentBySerial(String serial) {
		
		final String query = "SELECT * FROM panda_core.t_equipment where serial = :serial";
		Map<String,String> args = new HashMap<String, String>();
		args.put("serial", serial);
			
		Equipment e = jdbcTemplate.queryForObject(query, args, new RowMapper<Equipment>() {

			@Override
			public Equipment mapRow(ResultSet rs, int inte) throws SQLException {
				Equipment equip = new Equipment();
				equip.setAvailable(rs.getBoolean("available"));
				equip.setCreatedon(rs.getDate("createdon"));
				equip.setDateofmanufacture(rs.getDate("dateofmanufacture"));
				equip.setId(rs.getString("id"));
				equip.setModel(rs.getString("model"));
				equip.setName(rs.getString("name"));
				equip.setQuantity(rs.getBigDecimal("quantity"));
				equip.setSerial(rs.getString("serial"));
				equip.setCategory(new EquipCategory());
				equip.getCategory().setId(rs.getString("categoryid"));
				
				return equip;
			}
			
		});
		
		return e;
		
	}

}
