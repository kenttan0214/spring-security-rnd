package com.exam.security.models;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {
	private static final long serialVersionUID = -5943943568403341662L;
	private String authority;
	
	public UserAuthority(String authority){
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
