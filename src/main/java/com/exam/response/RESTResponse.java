package com.exam.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.exam.constant.ErrorConstant;
import com.exam.constant.TagConstant;

public class RESTResponse {
	
	protected ResponseEntity<Map<String, Object>> encapsulateHttpResponse(Map<String, Object> responseMap) {
		HttpStatus httpStatus = HttpStatus.OK;
		
		boolean success = true;
		
		if (responseMap.containsKey(TagConstant.STATUS)) {
			success = (Boolean) responseMap.get(TagConstant.STATUS);
		}
		
		if (!success) {
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Map<String, Object>>(responseMap, httpStatus);
	}
	
	protected ResponseEntity<Map<String, Object>> getUnknownErrorResponse() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put(TagConstant.STATUS, false);
		responseMap.put(TagConstant.ERROR_MESSAGE, ErrorConstant.UNKNOWN_ERROR);
		
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
	}
}
