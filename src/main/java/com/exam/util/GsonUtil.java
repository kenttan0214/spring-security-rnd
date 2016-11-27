package com.exam.util;

import com.google.gson.Gson;

public class GsonUtil {
	public static String toJSONString(Object object) {
		return new Gson().toJson(object);
	}
}
