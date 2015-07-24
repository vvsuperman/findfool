package com.foolrank.util;

import java.util.Map;

public class RequestUtil {

	public static String getStringParam(Map<String, String> params, String key, boolean needTrim) {
		if (params.get(key) == null) {
			return null;
		} else {
			return needTrim ? params.get(key).trim() : params.get(key);
		}
	}
	
	public static String getStringParam(Map<String, String> params, String key, String defaultValue, boolean needTrim) {
		if (params.get(key) == null) {
			return defaultValue;
		} else {
			return needTrim ? params.get(key).trim() : params.get(key);
		}
	}
	
	public static Integer getIntParam(Map<String, String> params, String key) {
		if (params.get(key) == null) {
			return null;
		} else {
			return Integer.parseInt(params.get(key).trim());
		}
	}
	
	public static Integer getIntParam(Map<String, String> params, String key, int defaultValue) {
		if (params.get(key) == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(params.get(key).trim());
		}
	}
}
