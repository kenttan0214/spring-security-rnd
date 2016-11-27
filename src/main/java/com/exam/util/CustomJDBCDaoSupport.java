package com.exam.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CustomJDBCDaoSupport extends JdbcDaoSupport {
	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	public int insert(final String SQL, final String fields[], final Object params[]) throws Exception {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL, fields);

					for (int i = 0; i < params.length; ++i) {
						int index = i + 1;

						if (params[i].getClass().equals(Integer.class)) {
							ps.setInt(index, (Integer) params[i]);
						} else if (params[i].getClass().equals(String.class)) {
							ps.setString(index, (String) params[i]);
						} else if (params[i].getClass().equals(Date.class)) {
							Date date = (Date) params[i];
							ps.setDate(index, new java.sql.Date(date.getTime()));
						}
					}
					return ps;
				}
			}, keyHolder);
			return keyHolder.getKey().intValue();
		} catch (DataAccessException dataAccessException) {
			throw new Exception(dataAccessException);
		} catch (Exception exception) {
			throw new Exception(exception);
		}
	}
}
