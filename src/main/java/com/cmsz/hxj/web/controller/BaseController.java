package com.cmsz.hxj.web.controller;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseController  {
	
	   protected void genErrOutputMap(Map<String, Object> outputMap, String msg) {
	        outputMap.put("code", -1);
	        outputMap.put("msg", msg);
	        outputMap.put("data", null);
	    }
	    
	    protected void genErrOutputMapWithCode(Map<String, Object> outputMap, String msg, int code) {
	        outputMap.put("code", code);
	        outputMap.put("msg", msg);
	        outputMap.put("data", null);
	    }
	    
	    protected void genSuccOutputMap(Map<String, Object> outputMap, Object data) {
	        outputMap.put("code", 0);
	        outputMap.put("msg", "ok");
	        outputMap.put("data", data);
	    }
	    
}
