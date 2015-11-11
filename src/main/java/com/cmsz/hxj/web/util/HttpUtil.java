package com.cmsz.hxj.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {
	
	public static String sendPost(String url, String content, Map<String, String> RequestProperties){
		  PrintWriter out = null;
	      BufferedReader in = null;
	      StringBuilder result =  new StringBuilder();
	      try {
	          URL realUrl = new URL(url);
	          Log.HTTP.info("[sendPost]url = " + url);
	          // 打开和URL之间的连接
	          URLConnection conn = realUrl.openConnection();
	          // 设置通用的请求属性
	          conn.setRequestProperty("accept", "*/*");
	          conn.setRequestProperty("connection", "Keep-Alive");
	          conn.setRequestProperty("Cache-Control", "no-cache");
	          conn.setRequestProperty("user-agent",
	                  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	          if(RequestProperties != null){
	        	  for( Entry<String, String> item: RequestProperties.entrySet()){
	        		  conn.setRequestProperty(item.getKey(), item.getValue());
	        	  }
	          }
	          // 发送POST请求必须设置如下两行
	          conn.setDoOutput(true);
	          conn.setDoInput(true);
	          
	          
	          
	          Log.HTTP.info("[sendPost]conn = " + conn.toString());
	          // 获取URLConnection对象对应的输出流
	          Log.HTTP.debug("system character set:"+ Charset.defaultCharset());
	          out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
	          
	          // 发送请求内容
	          out.print(content);
	          // flush输出流的缓冲
	          out.flush();
	          // 定义BufferedReader输入流来读取URL的响应
	          in = new BufferedReader(
	                  new InputStreamReader(conn.getInputStream(),"utf-8"));
	          String line;
	          while ((line = in.readLine()) != null) {
	        	  Log.HTTP.debug("SmsUtil read response: "+ line);
	              result.append(line);
	          }
	      } catch (Exception e) {
	    	  Log.HTTP.info("发送 POST 请求出现异常！"+e);
	          e.printStackTrace();
	      }
	      //使用finally块来关闭输出流、输入流
	      finally{
	          try{
	              if(out!=null){
	                  out.close();
	              }
	              if(in!=null){
	                  in.close();
	              }
	          }
	          catch(IOException ex){
	              ex.printStackTrace();
	          }
	      }
	      Log.HTTP.info("[sendPost]result = " + result);
	      return result.toString();
	}
}
