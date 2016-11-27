package com.exam.dao;

import com.exam.security.models.User;

public interface UserAccountDAO {
	public User getUserByUserName(String userName);
	public int createUser(String userName, String password, String email);
	public int getUserByUserNameOrEmailAvailability(String userName, String email);
}
