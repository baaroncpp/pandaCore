package com.fredastone.pandacore.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.VSaleApprovalReview;
import com.fredastone.pandacore.repository.VSaleApprovalReviewRepository;

@Repository
public class VSaleApprovalReviewRepoImpl implements VSaleApprovalReviewRepository {

	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	private static String GET_QUERY = "Select sr.* from panda_core.v_sale_approval_review sr WHERE sr.agentid = :agentid order by sr.itemid = :itemid order by sr.createdon DESC";
	
	
	@Override
	public List<VSaleApprovalReview> getAllSaleApprovalReview(String agentid, String itemid) {
		Map<String, String> map = new HashMap<>();
		map.put("agentid", agentid);
		map.put("itemid", itemid);
		
		return jdbcTemplate.query(GET_QUERY, map, new VSaleRepositoryMapper() );
		
	}
	
	private class VSaleRepositoryMapper implements RowMapper<VSaleApprovalReview>{

		@Override
		public VSaleApprovalReview mapRow(ResultSet rs, int arg1) throws SQLException {
			VSaleApprovalReview vs= new VSaleApprovalReview();
			vs.setAgentid(rs.getString("agentid"));
			vs.setCreatedon(rs.getDate("createdon"));
			vs.setId(rs.getString("id"));
			vs.setItemid(rs.getString("itemid"));
			vs.setReview(rs.getString("review"));
			vs.setSaleid(rs.getString("saleid"));
			
			return vs;
		}
		
	}

}
