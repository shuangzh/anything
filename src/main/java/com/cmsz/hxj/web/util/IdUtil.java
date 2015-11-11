package com.cmsz.hxj.web.util;

import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class IdUtil {
	public static String getTimeToMd5(){
		long lTime = System.currentTimeMillis();
		return DigestUtils.md5Hex(String.valueOf(lTime));
	}
	
	
	public static String genVerifyCode()
	{
		String base = "0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < 4 ; i++) {   
	        int number = random.nextInt(10);   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString(); 
	}
}
