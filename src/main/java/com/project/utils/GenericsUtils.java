package com.project.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GenericsUtils{
	
	public static Class<?> getGenericClass(Class<?> clazz){
		return getGenericClass(clazz, 0);
	}

	public static Class<?> getGenericClass(Class<?> clazz, int index){
		Type genType = clazz.getGenericSuperclass();

		if ((genType instanceof ParameterizedType)) {
			Type[] params = ((ParameterizedType) genType)
					.getActualTypeArguments();

			if ((params != null) && (params.length >= index - 1)) {
				return (Class<?>) params[index];
			}
		}
		return null;
	}

	public static String convertStreamToString(InputStream in) {

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		try {
			for (int n; (n = in.read(b)) != -1;) {
				out.append(new String(b, 0, n, "UTF-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();

	}

	public static String replaceBlank(String str) {
		Pattern p = Pattern.compile("\\s * |\t |\r |\n");

		Matcher m = p.matcher(str);
		String after = m.replaceAll("");
		return after;
	}
}