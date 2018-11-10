package com.fredastone.pandacore.pandacore.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.pandacore.entity.Devices;

@Repository
public class DevicesRepository implements IDeviceRespository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Devices> getAllDevice() {
		// TODO Auto-generated method stub
		List<Devices> devices = jdbcTemplate.query("Select * from devices.t_devices",  new DeviceMapper());
		return devices;
	}

	
	private static class DeviceMapper implements RowMapper<Devices>{

		@Override
		public Devices mapRow(ResultSet arg0, int arg1) throws SQLException {
			
			final Devices dv = new Devices();
			
			dv.setCreated_on(arg0.getTimestamp("created_on"));
			dv.setDevice_serial(arg0.getString("device_serial"));
			dv.setId(arg0.getString("id"));
			dv.setIs_active(arg0.getShort("is_active"));
			dv.setMake(arg0.getString("make"));
			dv.setModel(arg0.getString("model"));
			dv.setYof(arg0.getInt("yof"));
			
			return dv;
		}
		
	}
}
