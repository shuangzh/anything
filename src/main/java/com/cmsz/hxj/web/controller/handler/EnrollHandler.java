package com.cmsz.hxj.web.controller.handler;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.dao.model.ActivityEnrollPo;
import com.cmsz.hxj.web.service.ActivityService;
import com.cmsz.hxj.web.util.Constants;

@Component
public class EnrollHandler extends BaseHandler {

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
		
		ActivityEnrollPo activityEnrollPo = new ActivityEnrollPo();
		activityEnrollPo.setActivityId((String)param.get("acitivityID"));
		activityEnrollPo.setUid((String)param.get("uid"));
		activityEnrollPo.setPhone((String)param.get("phone"));
		
		int flag = activityService.activityEnroll(activityEnrollPo);
		switch(flag){
			case Constants.ActivityStatus.create : this.genSuccOutputMap();break;
			case -1 : this.genErrOutputMapWithCode("enroll faild, the activity is unExist!", flag);break;
			default :this.genErrOutputMapWithCode("enroll faild, cannot enroll right now ", flag);break;
		}
	}

}
