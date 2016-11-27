package com.exam.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.exam.model.UserAuthorities;
import com.exam.util.CustomJDBCDaoSupport;

@Repository("AuthoritiesDAO")
public class AuthoritiesDAOImpl extends CustomJDBCDaoSupport implements AuthoritiesDAO {

	@Override
	public List<UserAuthorities> getUserAuthoritiesByUserId(int userId) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT tbl_authorities.AUTHORITY_ID, tbl_authorities.AUTHORITY_DESC ");
		sqlBuilder.append("FROM tbl_user_authorities ");
		sqlBuilder.append("INNER JOIN tbl_authorities ");
		sqlBuilder.append("ON tbl_user_authorities.AUTHORITY_ID  = tbl_authorities.AUTHORITY_ID ");
		sqlBuilder.append("WHERE tbl_user_authorities.USER_ID = ?");

		List<UserAuthorities> userAuthorities = new ArrayList<UserAuthorities>();
		
		try {
			
			userAuthorities = getJdbcTemplate().query(sqlBuilder.toString(), 
					new Object[] { userId },
					new BeanPropertyRowMapper<UserAuthorities>(UserAuthorities.class));
			
		} catch (DataAccessException dataAccessException) {
			System.err.println("User " + userId + " have no authority");
			System.err.println(dataAccessException.getMessage());
		} catch (Exception e) {
			System.err.println("Unknown error occurred when select user authorities from user id " + userId);
			System.err.println(e.getMessage());
		}

		return userAuthorities;
	}
	
	@Override
	public List<UserAuthorities> getAuthoritiesList() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT AUTHORITY_ID, AUTHORITY_DESC FROM tbl_authorities");
		
		List<UserAuthorities> userAuthorities = new ArrayList<UserAuthorities>();
		
		try {

			userAuthorities = getJdbcTemplate().query(sqlBuilder.toString(),
					new BeanPropertyRowMapper<UserAuthorities>(UserAuthorities.class));

		} catch (DataAccessException dataAccessException) {
			System.err.println("No authority found!");
			System.err.println(dataAccessException.getMessage());
		} catch (Exception e) {
			System.err.println("Unknown error occurred when select authority");
			System.err.println(e.getMessage());
		}
		
		return userAuthorities;
	}
	
	@Override
	public int getAuthorityByDesc(String desc) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT AUTHORITY_ID FROM tbl_authorities ");
		sqlBuilder.append("WHERE AUTHORITY_DESC = ?");
		
		int authorityId = -1;
		
		try {
			authorityId = getJdbcTemplate().queryForObject(sqlBuilder.toString(), new Object[] { desc }, Integer.class);
		} catch (DataAccessException dataAccessException) {
			System.out.println("Authorities " + desc + " not found");
			System.err.println(dataAccessException.getMessage());
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}
		
		return authorityId;
	}

	@Override
	public int addUserAuthority(int userId, int authorityId) {
		StringBuilder insertStatement = new StringBuilder();
		insertStatement.append("INSERT INTO tbl_user_authorities ");
		insertStatement.append("(USER_ID, AUTHORITY_ID) ");
		insertStatement.append("VALUES (?, ?)");

		int result = -1;

		try {
			
			result = getJdbcTemplate().update(insertStatement.toString(), new Object[] { userId, authorityId });
			
		} catch (DataAccessException dataAccessException) {
			System.err.println("add user authority error, user id " + userId + " , authority id " + authorityId);
			System.err.println(dataAccessException.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return result;
	}

}
