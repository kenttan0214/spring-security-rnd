package com.exam.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestREST {
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> testRESTService(@RequestHeader("X-AUTH-TOKEN") String token) {
		Map<String, Object> statusObject = new HashMap<String, Object>();
		statusObject.put("STATUS", true);
		statusObject.put("Token", token);
		return new ResponseEntity<Map<String, Object>>(statusObject, HttpStatus.OK);
	}

	
}
