package com.cmsz.hxj.web.controller.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.dao.model.ActivityPo;
import com.cmsz.hxj.web.dao.model.UserPo;
import com.cmsz.hxj.web.service.ActivityService;
import com.cmsz.hxj.web.service.UserService;
import com.cmsz.hxj.web.util.Constants;

@Component
public class ActivityInviteHandler extends BaseHandler{

	@Autowired
	private ActivityService activityService;
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
		if (param.get("acitivityID") == null) {
			this.genErrOutputMapWithCode("param error, need acitivityID", ReturnCode.PARAM_ERROR);
			return;
		}
		
		List<UserPo> userPos = genInviteUsers(param);
		UserPo inviter = userService.getUser((String)param.get("phone"));
		if(inviter == null){
			this.genErrOutputMapWithCode("data error, inviter no exist", ReturnCode.OBJECT_NO_EXSIT);
			return;
		}
		ActivityPo activity = activityService.getActivity((String)param.get("acitivityID"));
		
		
		if(activity == null || activity.getStatus() != Constants.ActivityStatus.create){
			this.genErrOutputMapWithCode("data error, activity no exist", ReturnCode.OBJECT_NO_EXSIT);
			return;
		}
		
		try {
			activityService.activityInvite(inviter,userPos,activity);
		} catch (Exception e) {
			e.printStackTrace();
			this.genErrOutputMapWithCode("send message error", ReturnCode.SEND_MESSAGE_ERROR);
			return;
		}
		
		this.genSuccOutputMap();
	}
	
	private List<UserPo> genInviteUsers(HashMap<String, Object> param){
		List<String> phones = (List<String>) param.get("invitee");
		List<UserPo> userPos = new ArrayList<UserPo>();
		for(String phone:phones){
			UserPo userPo = new UserPo();
			userPo.setPhone(phone);
			userPos.add(userPo);
		}
		return userPos;
	}

}
