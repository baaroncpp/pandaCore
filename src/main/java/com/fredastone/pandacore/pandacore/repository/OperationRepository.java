package com.fredastone.pandacore.pandacore.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fredastone.pandacore.pandacore.entity.AccountFinancialInformation;
import com.fredastone.pandacore.pandacore.entity.FeesCollected;

import com.fredastone.pandacore.pandacore.entity.Token;


@Repository
@Transactional
public class OperationRepository implements IOperationalRepository{
	
	//Temporary, move these queries to xml -- Much better for maintainability
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	private static final String GET_ACCOUNT_FI_QUERY = "SELECT * FROM accounts.v_accountholderinfo where meter_number = :meterNumber";
	private static final String GET_TOKEN_QUERY ="SELECT * FROM payments.get_token(:amount :: numeric)";
	private static final String CREATE_FEE_COLLECTED_QUERY = "INSERT INTO payments.fees_collected (id,amount,token,meterNumber) VALUES (:id,:amount,:token,:meter)";
	
	@Override
	public List<AccountFinancialInformation> getAccountFinancialInformation(@NotNull String meterNumber) {
		
		try {
			Map<String,String> args = new HashMap<String,String>();
			
			args.put("meterNumber", meterNumber);
			
			List<AccountFinancialInformation> accts = jdbcTemplate.query(GET_ACCOUNT_FI_QUERY,args, new AccountFinancialInformationMapper());
			return accts;
		}
			
		catch (DataAccessException dae) {
			
			  dae.printStackTrace();
	
			}
		

		return null;
	}

	
	
	private static class AccountFinancialInformationMapper implements RowMapper<AccountFinancialInformation>{

		@Override
		public AccountFinancialInformation mapRow(ResultSet rs, int arg1) throws SQLException {
			AccountFinancialInformation afi = new AccountFinancialInformation();
			afi.setAccountCreatedOn(rs.getTimestamp("account_created_on"));
			afi.setAccountId(rs.getString("account_id"));
			afi.setActiveAccount(rs.getShort("active_account"));
			afi.setFeesDueDate(rs.getTimestamp("due_date"));
			afi.setDeviceSerial(rs.getString("device_serial"));
			afi.setEmail(rs.getString("email"));
			afi.setFeesAddedOn(rs.getTimestamp("fees_added_on"));
			afi.setFeesCleared(rs.getShort("fees_cleared"));
			afi.setFeesDescription(rs.getString("description"));
			afi.setBalanceDue(rs.getFloat("amount"));
			afi.setFirstName(rs.getString("first_name"));
			afi.setIsTransactional(rs.getShort("is_transactional"));
			afi.setLastName(rs.getString("last_name"));
			afi.setMeterNumber(rs.getString("meter_number"));
			afi.setMobileNumber(rs.getString("mobile_number"));
			afi.setOtherNames(rs.getString("other_names"));
	
			
			
			return afi;
		}
		
	}



	@Override
	public String getPaymentToken(float amount) {
		

		try {
			Map<String,Float> args = new HashMap<String,Float>();
			
			args.put("amount", amount);
			final String token  = 
					jdbcTemplate.queryForObject(GET_TOKEN_QUERY,args, String.class);
			return token;
		}
			
		catch (DataAccessException dae) {
			
			  dae.printStackTrace();
	
			}
		

		return null;
	}



	@Override
	public int createNewCollectedFee(FeesCollected feeCollected) {

		try {
			Map<String,Object> args = new HashMap<String,Object>();
			
			args.put("id", UUID.randomUUID().toString());
			args.put("amount", feeCollected.getAmount());
			args.put("token", feeCollected.getToken());
			args.put("meter", feeCollected.getMeterNumber());
			
			return jdbcTemplate.update(CREATE_FEE_COLLECTED_QUERY,args);
		
		}
			
		catch (DataAccessException dae) {
			
			  dae.printStackTrace();
	
			}
		return 0;
	}
	
	

}
