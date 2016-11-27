package com.exam.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exam.bo.UserAccountBO;
import com.exam.response.RESTResponse;
import com.exam.security.models.User;


@RestController
@RequestMapping("user")
public class UserAccountREST extends RESTResponse {
	@Autowired
	private UserAccountBO userAccountBO;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createNormalUser(@RequestBody User user) {
		try {
			Map<String, Object> normalUserMap = userAccountBO.createNormalUser(user);
			return encapsulateHttpResponse(normalUserMap);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return getUnknownErrorResponse();
		}
	}
}
