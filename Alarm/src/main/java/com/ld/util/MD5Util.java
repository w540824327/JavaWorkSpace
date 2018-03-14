package com.ld.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class MD5Util {

	public static String md5(String message){
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] b = md.digest(message.getBytes());
			return Base64.encodeBase64String(b);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(md5("admin"));
	}
}
