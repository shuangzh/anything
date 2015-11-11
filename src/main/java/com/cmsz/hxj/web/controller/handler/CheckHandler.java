package com.cmsz.hxj.web.controller.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.dao.model.UserPo;
import com.cmsz.hxj.web.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class CheckHandler extends BaseHandler {
	
	private String[] returnParam = {"uid","phone","name","sex","avatarURL","avatarId"};

	@Autowired
	private UserService userService;
	
	@Override
	public void handle(HashMap<String, Object> param) {
		if (param.get("phone") == null) {
			this.genErrOutputMapWithCode("param error, need phone", ReturnCode.PARAM_ERROR);
			return;
		}

		if (param.get("uid") == null) {
			this.genErrOutputMapWithCode("param error, need uid", ReturnCode.PARAM_ERROR);
			return;
		}
		try{
			List<String> phones = (List<String>) param.get("phones");
			List<UserPo> userPoList = userService.getUsers(phones);
			int num = userPoList.size();
			JSONArray jsonArray = new JSONArray();
			for(UserPo userPo : userPoList){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				this.geneDataMap(resultMap,userPo,returnParam);
				jsonArray.add(JSONObject.fromObject(resultMap));
			}
			dataMap.put("num", num);
			dataMap.put("users", jsonArray);
			this.genSuccOutputMap();
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
			this.genErrOutputMapWithCode("login failed", ReturnCode.LOGIN_FAILED);
		} catch (SecurityException e) {
			e.printStackTrace();
			this.genErrOutputMapWithCode("login failed", ReturnCode.LOGIN_FAILED);
		}
	}

}
