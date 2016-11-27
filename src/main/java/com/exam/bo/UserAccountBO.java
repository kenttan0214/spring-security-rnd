package com.exam.bo;

import java.util.Map;

import com.exam.security.models.User;

public interface UserAccountBO {
	public Map<String, Object> createNormalUser(User user);
	public Map<String, Object> getUserByUserName(String userName);
}
