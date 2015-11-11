package com.cmsz.hxj.web.controller.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.service.UserService;

@Component
public class SendVerifyCodeHandler extends BaseHandler {
	@Autowired
	private UserService userService;
	

	@Override
	public void handle(HashMap<String, Object> param) {
	 if( param.get("phone") ==null ){
		 this.genErrOutputMapWithCode("para error, need phone", ReturnCode.PARAM_ERROR);
		 return;
	 }
	 String phone=(String)param.get("phone");
	 try {
		userService.sendVerifyCode(phone);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 this.genSuccOutputMap();
	}

}
