package com.cmsz.hxj.web.controller.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.util.Log;
import com.cmsz.hxj.web.util.ThreadMap;

public abstract class BaseHandler {
	
	protected ThreadMap<String, Object> dataMap;
	protected ThreadMap<String, Object> outputMap;

	public BaseHandler() {
		outputMap = new ThreadMap<String, Object>();
		dataMap = new ThreadMap<String, Object>();
		genErrOutputMap("Interface Error!");
	}
	
	
	
	public HashMap<String, Object> InvokeHandle(HashMap<String, Object> param){
		outputMap.reset();
		dataMap.reset();
		this.handle(param);
		return (HashMap<String, Object>) outputMap.getMap();
	}

	public abstract void handle(HashMap<String, Object> param);

	public void genErrOutputMap(String msg) {
		outputMap.put("code", ReturnCode.INTERFACE_ERROR);
		outputMap.put("msg", msg);
	}

	public void genErrOutputMapWithCode(String msg, int code) {
		outputMap.put("code", code);
		outputMap.put("msg", msg);
	}

	public void genSuccOutputMap() {
		outputMap.put("code", ReturnCode.OK);
		outputMap.put("msg", "ok");
		outputMap.put("data", dataMap);
	}
	
	public void genSuccOutputMap(String dataResult) {
		outputMap.put("code", ReturnCode.OK);
		outputMap.put("msg", "ok");
		outputMap.put("data", dataResult);
	}
	public void genSuccOutputMap(Object data) {
		outputMap.put("code", ReturnCode.OK);
		outputMap.put("msg", "ok");
		outputMap.put("data", data);
	}
	
	/**
	 * 通过java反射,获取对象每个属性的值
	 * @param t
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public <T> void geneDataMap(Map<String, Object> dataMap,T t,String[] returnParam) throws NoSuchMethodException, SecurityException{
		Class tclass = t.getClass();
		for(String fieldName : returnParam){
			StringBuilder filedValue = new StringBuilder();
			Method getterMethod = genGetterMethod(tclass,fieldName);
			
			if(getterMethod != null){
				try {
					Object objTemp = getterMethod.invoke(t, new Object[]{});
					if(objTemp == null){
						filedValue.append("");
					}else{
						filedValue.append(objTemp.toString());
					}
					dataMap.put(fieldName, filedValue.toString());
				} catch (Exception e) {
					Log.SERVICE.error("The Method invoke error");
					throw e;
				} finally{
					continue;
				}
			}else{
				continue;
			}
		}
	}

	private Method genGetterMethod(Class tclass, String fieldName) throws NoSuchMethodException,SecurityException {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	        String getter = "get" + firstLetter + fieldName.substring(1);    
	        Method method = tclass.getMethod(getter, new Class[] {});
	        return method;
		} catch (NoSuchMethodException e) {
			Log.SERVICE.error("No Such Method");
			throw e;
		} catch (SecurityException e) {
			Log.SERVICE.error("No Security Method");
			throw e;
		}
	}
	
}
