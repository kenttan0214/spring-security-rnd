package com.exam.security.services;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.exam.bo.UserAccountBO;
import com.exam.constant.TagConstant;
import com.exam.security.models.User;
import com.exam.util.GsonUtil;

import io.jsonwebtoken.Claims;

@Service
public class TokenAuthenticationService {
	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

	@Autowired
	private TokenGenerateService tokenGenerator;
	
	@Autowired
	private UserAccountBO userAccountBO;

	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final User user = authentication.getDetails();
		response.addHeader(AUTH_HEADER_NAME, tokenGenerator.createToken(user));
	}
	
	public void addResponse(HttpServletResponse response, User user) throws IOException {
		
		//Remove the unwanted column
		user.setAuthorities(null);
		user.setPassword(null);
		
		response.getWriter().write(GsonUtil.toJSONString(user));
		response.getWriter().flush();
		response.getWriter().close();
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final Claims tokenClaims = tokenGenerator.parseToken(token);

			if (tokenClaims != null) {
				String userName = tokenClaims.getSubject();
				Map<String, Object> userMap = userAccountBO.getUserByUserName(userName);
				
				if(userMap.containsKey(TagConstant.STATUS) && 
						userMap.containsKey(TagConstant.USER_DETAILS)) {
					
					boolean success = (Boolean) userMap.get(TagConstant.STATUS);
					User user = (User) userMap.get(TagConstant.USER_DETAILS);
					
					if (success && user != null) {
						return new UserAuthentication(user);
					}
					
				}
			}
		}
		return null;
	}

}
