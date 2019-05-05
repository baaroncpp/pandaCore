package com.fredastone.pandacore.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.entity.LoginUser;
import com.fredastone.pandacore.entity.Role;
import com.fredastone.pandacore.repository.UserRepositoryCustom;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	private static final String GET_USER_QUERY = "Select * from panda_core.v_login_user WHERE username = :loginuser or email = :loginuser";
	
	@Transactional
	@Override
	public LoginUser getLoginUser(String username) {
		Map<String,String> args = new HashMap<>();
		args.put("loginuser", username);
		
		List<LoginUser> users = jdbcTemplate.query(GET_USER_QUERY, args, new LoginUserRowMapper());
		
		if(users == null || users.isEmpty())
			return null; 
		
		final LoginUser user  = users.get(0);
		if(users.size() == 1) {
			
			List<Role> role = new ArrayList<>(1);
			if(user.getRolename()== null)
			{
				user.setRoles(role);
				return user;
			}
			
			role.add(new Role(RoleName.valueOf(user.getRolename())));
			user.setRoles(role);
			
			
			
			return user;
 		}
		
		final List<Role> roles = users.stream()
                .map(user1 -> new Role(RoleName.valueOf(user1.getRolename())))
                .collect(Collectors.toList());
		
		user.setRoles(roles);
		
		return user;
	}
	
	private class LoginUserRowMapper implements RowMapper<LoginUser>{

		@Override
		public LoginUser mapRow(ResultSet rs, int arg1) throws SQLException {
			LoginUser user = new LoginUser();
			user.setEmail(rs.getString("email"));
			user.setId(rs.getString("id"));
			user.setIsactive(rs.getBoolean("isactive"));
			user.setIsapproved(rs.getBoolean("isapproved"));
			user.setPassword(rs.getString("password"));
			user.setRolename(rs.getString("rolename"));
			user.setUsername(rs.getString("username"));
			
			return user;
		}
	}

}
