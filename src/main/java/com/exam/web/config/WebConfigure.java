package com.exam.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.exam")
@PropertySource("classpath:database.properties")
public class WebConfigure {

	@Value("${jdbc.driverClassName}")
	private String jdbcDriver;

	@Value("${jdbc.url}")
	private String dbUrl;

	@Value("${jdbc.username}")
	private String dbUserName;

	@Value("${jdbc.password}")
	private String dbPassword;

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(jdbcDriver);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUserName);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

	// To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
