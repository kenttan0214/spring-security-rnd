package com.exam.dao;

import java.util.List;

import com.exam.model.UserAuthorities;

public interface AuthoritiesDAO {
	public List<UserAuthorities> getUserAuthoritiesByUserId(int userId);
	public List<UserAuthorities> getAuthoritiesList();
	public int addUserAuthority(int userId, int authorityId);
	public int getAuthorityByDesc(String desc);
}
