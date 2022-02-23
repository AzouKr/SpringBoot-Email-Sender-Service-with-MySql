package com.demoproject.Demoproject;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {
	
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	      User usr = new User();
	      usr.setFirstName(rs.getString("FirstName"));
	      usr.setLastName(rs.getString("LastName"));
	      usr.setEmail(rs.getString("Email"));
	      usr.setPassword(rs.getString("Password"));
	      return usr;
	    }

}
