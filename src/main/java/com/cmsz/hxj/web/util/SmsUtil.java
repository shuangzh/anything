package com.cmsz.hxj.web.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class SmsUtil {
	
	private static String url="http://112.54.207.39:8001/open/sms/send";
	private static String apiKey = "4157c23b8f974945a7c697528ba6ad9b";
	private static String secretKey = "4a04e6f4af144def899cc8736e70ac2c";
	private static String SAN="1069060600860161";
	private static String receiptNotificationURL="null";
	
	private static String RSP_SUCCESS="200";
	
	public static void sendSms(String phone, String content) throws Exception{
		JSONObject json=new JSONObject();
		json.put("destAddr", phone);
		json.put("sourceAddr", SAN);
		json.put("message", content);
		json.put("needReceipt", 0);
		json.put("apiKey", apiKey);
		json.put("secretKey", secretKey);
		json.put("receiptNotificationURL", receiptNotificationURL);
		Map<String, String> requestProperties=new HashMap<String, String>();
		requestProperties.put("Content-Type", "application/json; charset=utf-8");
		requestProperties.put("Accept", "application/json");
		Log.HTTP.info("Send sms:"+json.toString());
		String result=HttpUtil.sendPost(url, json.toString(), requestProperties);
		json=JSONObject.fromObject(result);
		if(! json.getString("resultCode").equals(RSP_SUCCESS)){
			Log.HTTP.error("snnd sms failed! rsp:"+result);
			throw new Exception("send sms failed!");
		}
	}
	
	
}
