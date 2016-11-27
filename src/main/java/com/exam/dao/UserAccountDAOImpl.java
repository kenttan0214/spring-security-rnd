package com.exam.dao;



import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.exam.mapper.UserRowMapper;
import com.exam.security.models.User;
import com.exam.util.CustomJDBCDaoSupport;

@Repository("UserAccountDAO")
public class UserAccountDAOImpl extends CustomJDBCDaoSupport implements UserAccountDAO {
	@Override
	public User getUserByUserName(String userName) {
		final String sql = "SELECT * FROM tbl_user WHERE USER_NAME = ?";
		User user = null;
		
		try {
			user = getJdbcTemplate().queryForObject(sql, new Object[] { userName }, new UserRowMapper());
		} catch (DataAccessException dataAccessException) {
			System.err.println("User " + userName + " not found!");
			System.err.println(dataAccessException.getMessage());
		} catch (Exception e) {
			System.err.println("Unknown error occurred when select user " + userName);
			System.err.println(e.getMessage());
		}
		return user;
	}
	
	@Override
	public int getUserByUserNameOrEmailAvailability(String userName, String email) {
		String sql = "SELECT COUNT(USER_NAME) AS Availability FROM tbl_user WHERE USER_NAME = ? OR USER_EMAIL = ?";
		
		int numOfUser = -1;
		try {
			numOfUser = (Integer) getJdbcTemplate().queryForObject(sql, new Object[] { userName,  email}, Integer.class);
		} catch (DataAccessException dataAccessException) {
			System.err.println(dataAccessException.getMessage());
		} catch (Exception e) {
			System.err.println("Unknown error occurred when select user by username & email");
			System.err.println(e.getMessage());
		}
		
		return numOfUser;
	}
	
	@Override
	public int createUser(String userName, String password, String email) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_user ");
		sql.append("(USER_NAME, USER_EMAIL, USER_PASSWORD, ACCOUNT_EXPIRED_DATE) ");
		sql.append("VALUES (?, ?, ?, ?)");

		int userId = -1;
		
		try {
			userId = insert(sql.toString(), 
					new String[] {"USER_ID"}, 
					new Object[] { userName, email, password, new Date() });
		} catch (Exception e) {
			System.err.println("Create user " + userName + " failed");
			System.err.println(e.getMessage());
		}
		
		return userId;
	}

}
