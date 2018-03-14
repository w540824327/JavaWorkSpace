package com.ld.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getDiffObject(T t1, T t2) {
		Map<String, Object> map = getDiffField(t1, t2);
		T result = null;
		try {
			if(map != null && map.size() > 0) {
				Class<?> clazz = Class.forName(t1.getClass().getName());
				result = (T) clazz.newInstance();
				for(Map.Entry<String, Object> me : map.entrySet()) {
					Field field = clazz.getDeclaredField(me.getKey());
					field.setAccessible(true);
					field.set(result, me.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static <T> Map<String, Object> getDiffField(T t1, T t2) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Class<?> clazz1 = t1.getClass();
		Class<?> clazz2 = t2.getClass();
		Field[] fields1 = clazz1.getDeclaredFields();
		Field[] fields2 = clazz2.getDeclaredFields();
		for(int i = 0; i < fields1.length; i++) {
			try {
				fields1[i].setAccessible(true);
				fields2[i].setAccessible(true);
				Object obj1 = fields1[i].get(t1);
				Object obj2 = fields2[i].get(t2);
				if((obj1 != null && !obj1.equals(obj2)) || (obj2 != null && !obj2.equals(obj1))) {
					resultMap.put(fields2[i].getName(), obj2);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}
	
	public static boolean isNull(Object obj) {
		boolean result = true;
		if(obj != null) {
			Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			if(fields != null && fields.length > 0) {
				for(Field field : fields) {
					try {
						field.setAccessible(true);
						Object value = field.get(obj);
						if(value != null) {
							result = false;
							break;
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
}
