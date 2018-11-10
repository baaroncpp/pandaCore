package com.fredastone.pandacore.pandacore.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.pandacore.entity.UserAccount;

@Repository
public class AccountRepository implements IAccountsRepository {
	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	private static String ACCOUNT_QUERY = "Select * from accounts.t_user where meter_number = :meterNo and is_transactional = 1 and is_enabled = 1";

	@Override
	public UserAccount getTransAccountByMeterNo(String meterNumber) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("meterNo", meterNumber);
		
		try {
			
			UserAccount acct = jdbcTemplate.queryForObject(ACCOUNT_QUERY,argMap, new UserAccountMapper());
			return acct;
			
		} catch (DataAccessException dae) {
			if(dae instanceof EmptyResultDataAccessException) {
				System.out.print("No result found that matches "+meterNumber);
			}else {
			
				dae.printStackTrace();
			}

		}
		
		return null;

	}

	@Override
	public int checkValidAccountExists(String meterNumber) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	private static class UserAccountMapper implements RowMapper<UserAccount>{

		@Override
		public UserAccount mapRow(ResultSet rs, int arg1) throws SQLException {
			
			UserAccount acct = new UserAccount();
			
			acct.setCreatedOn(rs.getTimestamp("created_on"));
			acct.setEmail(rs.getString("email"));
			acct.setFirstName(rs.getString("first_name"));
			acct.setId(rs.getString("id"));
			acct.setIsTransactional(rs.getShort("is_transactional"));
			acct.setLastName(rs.getString("last_name"));
			acct.setMobileNumber(rs.getString("mobile_number"));
			acct.setOtherNames(rs.getString("other_names"));
			acct.setMeterNumber(rs.getString("meter_number"));
			acct.setIsEnabled(rs.getShort("is_enabled"));
			
			return acct;
		}
		
	}
}
