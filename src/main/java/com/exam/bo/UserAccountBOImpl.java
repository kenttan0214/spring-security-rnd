package com.exam.bo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.exam.checker.Checker;
import com.exam.constant.Constant;
import com.exam.constant.ErrorConstant;
import com.exam.dao.AuthoritiesDAO;
import com.exam.dao.UserAccountDAO;
import com.exam.model.UserAuthorities;
import com.exam.response.UserAccountResponse;
import com.exam.security.models.User;
import com.exam.security.models.UserAuthority;
import com.mysql.jdbc.StringUtils;


@Repository("UserAccountBO")
public class UserAccountBOImpl extends UserAccountResponse implements UserAccountBO {
	
	@Autowired
	private UserAccountDAO userAccountDAO;
	@Autowired
	private AuthoritiesDAO authoritiesDAO;

	public Map<String, Object> createNormalUser(User user) {
		int userId = -1;
		boolean success = false;
		String errorMsg = ErrorConstant.UNKNOWN_ERROR;

		String userName = user.getUsername();
		String password = user.getPassword();
		String userEmail = user.getEmail();
		
		if (isValidUser(userName, password, userEmail)) {
			int existingUser = userAccountDAO.getUserByUserNameOrEmailAvailability(userName, userEmail);
			
			if (existingUser == 0) {
				userId = userAccountDAO.createUser(userName, password, userEmail);

				if (userId > 0) {
					int normalUserAuthorityId = authoritiesDAO.getAuthorityByDesc(Constant.USER.toUpperCase());
					
					if (normalUserAuthorityId > 0 && grantAuthorityToNormalUser(userId, normalUserAuthorityId)) {
						success = true;
					} else {
						errorMsg = ErrorConstant.FAILED_GRANT_AUTHORITY;
					}
				} else {
					errorMsg = ErrorConstant.FAILED_CREATE_USER;
				}
			} else {
				errorMsg = ErrorConstant.USER_NAME_EXIST;
			}
		} else {
			errorMsg = ErrorConstant.INVALID_INPUT;
		}
		return getCreateUserResponse(userId, success, errorMsg);
	}
	
	public Map<String, Object> getUserByUserName(String userName) {
		boolean success = false;
		String errorMsg = ErrorConstant.UNKNOWN_ERROR;
		
		User user = userAccountDAO.getUserByUserName(userName);
		
		if (user != null) {
			List<UserAuthorities> userAuthorities = authoritiesDAO.getUserAuthoritiesByUserId(user.getUserId());
			user.setAuthorities(setUserAuthorities(userAuthorities));
			success = true;
		} else {
			errorMsg = ErrorConstant.USER_NOT_EXIST;
		}
		
		return getUserResponse(user, success, errorMsg);
	}
	
	private boolean grantAuthorityToNormalUser(int userId, int userAuthorityId) {
		int affectedRow = authoritiesDAO.addUserAuthority(userId, userAuthorityId);
		return affectedRow > 0;
	}
	
	private Set<UserAuthority> setUserAuthorities(List<UserAuthorities> userAuthorities) {
		Set<UserAuthority> authList = new HashSet<UserAuthority>();
		
		for (UserAuthorities authority : userAuthorities) {
			String role = authority.getAuthorityDesc();
			// you can also add different roles here
			// for example, the user is also an admin of the site, then you can add
			// ROLE_ADMIN
			// so that he can view pages that are ROLE_ADMIN specific
			if (role != null && role.trim().length() > 0) {
				if (role.equalsIgnoreCase(Constant.ADMIN)) {
					authList.add(new UserAuthority(Constant.ADMIN_ROLE));
				} else if (role.equalsIgnoreCase(Constant.USER)) {
					authList.add(new UserAuthority(Constant.USER_ROLE));
				}
			}
		}
		
		return authList;
	}
	
	private boolean isValidUser(String userName, String password, String email) {
		return (!StringUtils.isNullOrEmpty(userName) && !StringUtils.isNullOrEmpty(password)
				&& !StringUtils.isNullOrEmpty(email) && Checker.isValidEmail(email));
	}

}
