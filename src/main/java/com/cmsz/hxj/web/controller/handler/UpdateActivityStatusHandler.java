package com.cmsz.hxj.web.controller.handler;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.dao.model.ActivityPo;
import com.cmsz.hxj.web.service.ActivityService;

@Component
public class UpdateActivityStatusHandler extends BaseHandler {

	@Autowired
	private ActivityService activityService;
	
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
		ActivityPo activityPo = genActivityPo(param);
		int status = (Integer)param.get("status");
		activityService.UpdateActivityStatus(activityPo,status);
		
		this.genSuccOutputMap();
	}

	private ActivityPo genActivityPo(HashMap<String, Object> param) {
		ActivityPo activityPo = new ActivityPo();
		activityPo.setActivityId((String)param.get("acitivityID"));
		activityPo.setUid((String)param.get("uid"));
		activityPo.setPhone((String)param.get("phone"));
		return activityPo;
	}

}
