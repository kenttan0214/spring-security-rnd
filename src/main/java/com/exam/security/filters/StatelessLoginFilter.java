package com.exam.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.exam.security.models.User;
import com.exam.security.services.TokenAuthenticationService;
import com.exam.security.services.UserAuthentication;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter  {
	private final TokenAuthenticationService tokenAuthenticationService;
	
	public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.tokenAuthenticationService = tokenAuthenticationService;
		setAuthenticationManager(authManager);
	}

	// This method will trigger to authenticate user by its user name and password
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		Authentication authentication = getAuthenticationManager().authenticate(loginToken);
		return authentication;
	}

	// When successful authenticate user then this method will be triggered
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authentication) throws IOException, ServletException {
		
		// Get user object from the Authentication object
		final User authenticatedUser = (User) authentication.getPrincipal();
		// Create an UserAuthentication object to let Spring Security know that this user is a authenticated user
		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);
		
		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthentication(response, userAuthentication);
		tokenAuthenticationService.addResponse(response, authenticatedUser);
		
		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);

	}
}
