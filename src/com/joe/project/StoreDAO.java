package com.joe.project;

import java.sql.Types;
import java.util.Map;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class StoreDAO {
	private JdbcTemplate access;
	
	@Autowired
	public void setAccess(DataSource dataSource){
		this.access = new JdbcTemplate(dataSource);
	}
	
	public void doSignup(Object[] params) {
		int types[] = new int[]{Types.VARCHAR, Types.VARCHAR};
		String insertSql = "INSERT INTO User (username, password) VALUES (?, ?)";
		
		// execute insert query to insert the data
		// return number of row / rows processed by the executed query
		int row = this.access.update(insertSql, params, types);
		System.out.println(row + " row inserted.");
	}
	
	public int doAuth(String username, String password){
		List<Map<String, Object>> results = this.access.queryForList("SELECT * FROM User WHERE username = '" + username + "' AND password = '" + password + "'");

		return results.size();
	}
	
	public int doAuthApi(String apiKey){
		List<Map<String, Object>> results = this.access.queryForList("SELECT * FROM Api WHERE apikey = '" + apiKey + "'");

		return results.size();
	}
	
	public void doLog(Object[] params){
		int types[] = new int[]{Types.VARCHAR, Types.VARCHAR};
		String insertSql = "INSERT INTO Log (user, action) VALUES (?, ?)";
		
		// execute insert query to insert the data
		// return number of row / rows processed by the executed query
		int row = this.access.update(insertSql, params, types);
		System.out.println(row + " row inserted.");
	}
}
