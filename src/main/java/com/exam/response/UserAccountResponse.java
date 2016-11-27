package com.exam.response;

import java.util.HashMap;
import java.util.Map;

import com.exam.constant.TagConstant;
import com.exam.security.models.User;

public class UserAccountResponse {

	public Map<String, Object> getCreateUserResponse(int id, boolean success, String errorMsg) {
		Map<String, Object> responseMap = new HashMap<String, Object>();

		if (!success) {
			responseMap.put(TagConstant.ERROR_MESSAGE, errorMsg);
		}
		responseMap.put(TagConstant.USER_ID, id);
		responseMap.put(TagConstant.STATUS, success);

		return responseMap;
	}

	public Map<String, Object> getUserResponse(User user, boolean success, String errorMsg) {
		Map<String, Object> responseMap = new HashMap<String, Object>();

		if (!success) {
			responseMap.put(TagConstant.ERROR_MESSAGE, errorMsg);
		}
		
		responseMap.put(TagConstant.USER_DETAILS, user);
		responseMap.put(TagConstant.STATUS, success);
		
		return responseMap;
	}
}
