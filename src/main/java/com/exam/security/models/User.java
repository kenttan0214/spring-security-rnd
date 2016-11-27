package com.exam.security.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements UserDetails {

	private static final long serialVersionUID = -238377548737661910L;
	private int userId;
	private String username;
	private String password;
	private String email;
	private boolean isAccountNonExpired = true;
	private boolean isAccountNonLocked = true;
	private boolean isCredentialsNonExpired = true;
	private boolean isEnabled = true;
	private Set<UserAuthority> authorities = new HashSet<UserAuthority>();

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonIgnore
	public String getEmail() {
		return email;
	}
	
	@JsonProperty
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}
	
	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}
	
	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public Set<UserAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Set<UserAuthority> authorities) {
		this.authorities = authorities;
	}
}
