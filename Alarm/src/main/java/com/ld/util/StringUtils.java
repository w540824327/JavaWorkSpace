package com.ld.util;

import java.util.Collection;

public class StringUtils {

	public static boolean isBlank(String value) {
		if(value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(String[] arr) {
		if(arr == null || arr.length == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(Collection<String> collection) {
		if(collection == null || collection.size() == 0) {
			return true;
		}
		return false;
	}
}
