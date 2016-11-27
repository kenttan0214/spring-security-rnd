package com.exam.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.exam.security.models.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("USER_ID"));
		user.setUsername(rs.getString("USER_NAME"));
		user.setPassword(new BCryptPasswordEncoder().encode(rs.getString("USER_PASSWORD")));
		user.setEnabled(rs.getBoolean("ACCOUNT_ENABLE"));
		user.setCredentialsNonExpired(!rs.getBoolean("PASSWORD_EXPIRED"));
		user.setAccountNonLocked(!rs.getBoolean("ACCOUNT_LOCKED"));
		user.setAccountNonExpired(getAccountExpiredStatus(rs.getDate("ACCOUNT_EXPIRED_DATE")));
		return user;
	}

	private boolean getAccountExpiredStatus(Date expiredDate) {
		Date today = new Date();
		return expiredDate.after(today);
	}

}
