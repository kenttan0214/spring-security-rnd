package com.exam.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exam.security.filters.StatelessAuthenticationFilter;
import com.exam.security.filters.StatelessLoginFilter;
import com.exam.security.services.TokenAuthenticationService;
import com.exam.security.services.UserDetailsCustomService;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsCustomService userDetails;

	@Autowired
	private TokenAuthenticationService tokenAuthService;

	public SecurityConfiguration() {
		super(true);
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.exceptionHandling().and()
		.anonymous().and()
		.authorizeRequests()
//		.antMatchers(HttpMethod.GET, "/guest/**").permitAll()
		.antMatchers("/api/test/**").hasAnyRole("ADMIN")
//		.antMatchers("/test/**").hasAnyRole("USER")
//		.antMatchers("/guest/**").hasAnyRole("GUEST")
//		.anyRequest().permitAll()
		.and()
		// custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
		.addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
		// custom Token based authentication based on the header previously given to the client
		.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthService), UsernamePasswordAuthenticationFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected UserDetailsCustomService userDetailsService() {
		return userDetails;
	}
}
