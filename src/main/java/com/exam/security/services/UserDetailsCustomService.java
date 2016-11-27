package com.exam.security.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exam.bo.UserAccountBO;
import com.exam.constant.ErrorConstant;
import com.exam.constant.TagConstant;
import com.exam.security.models.User;

@Service
public class UserDetailsCustomService implements UserDetailsService {
	
	@Autowired
	private UserAccountBO userAccountBO;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// retrieve user data from DB
		Map<String, Object> userMap = userAccountBO.getUserByUserName(userName);

		User user = null;
		
		if(userMap.containsKey(TagConstant.STATUS) && 
				userMap.containsKey(TagConstant.USER_DETAILS)) {
			
			boolean success = (Boolean) userMap.get(TagConstant.STATUS);
			user = (User) userMap.get(TagConstant.USER_DETAILS);
			
			if (!success && user == null) {
				throw new UsernameNotFoundException(ErrorConstant.USER_NOT_EXIST);
			}
		}
		
		return user;
	}
}
