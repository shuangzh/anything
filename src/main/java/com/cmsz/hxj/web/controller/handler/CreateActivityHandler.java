package com.cmsz.hxj.web.controller.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.dao.model.ActivityLocationPo;
import com.cmsz.hxj.web.dao.model.ActivityPo;
import com.cmsz.hxj.web.entity.ActivityEntity;
import com.cmsz.hxj.web.service.ActivityService;


@Component
public class CreateActivityHandler extends BaseHandler{
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
		ActivityEntity activityEntity = geneActivityPo(param);
		String activityId = activityService.createActivity(activityEntity);
		dataMap.put("activityID", activityId);
		this.genSuccOutputMap();
	}

	private ActivityEntity geneActivityPo(HashMap<String, Object> param) {
		ActivityPo activityPo = new ActivityPo();
		activityPo.setUid((String)param.get("uid"));
		activityPo.setPhone((String)param.get("phone"));
		activityPo.setActivityName((String)param.get("activityName"));
		activityPo.setAddress((String)param.get("address"));
		activityPo.setProvince((String)param.get("province"));
		activityPo.setCity((String)param.get("city"));
		activityPo.setBeginTime((String)param.get("beginTime"));
		activityPo.setEndTime((String)param.get("endTime"));
		activityPo.setDeadlineTime((String)param.get("deadlineTime"));
		activityPo.setImgURL((String)param.get("imgURL"));
		activityPo.setImgId((String)param.get("imgId"));
		activityPo.setLimitNum((Integer)param.get("limitNum"));
		activityPo.setActivityType((Integer)param.get("activityType"));
		activityPo.setIsOpen((Integer)param.get("isOpen"));
		activityPo.setTraceType((Integer)param.get("traceType"));
		activityPo.setRadius((Double)param.get("radius"));
		activityPo.setLongitude((Double)param.get("longitude"));
		activityPo.setLatitude((Double)param.get("latitude"));
		activityPo.setLocationNum((Integer)param.get("locationsNum"));
		
		List<ActivityLocationPo> activityLocations = new ArrayList<ActivityLocationPo>();
		List<HashMap<String, Object>> locations = (List<HashMap<String, Object>>)param.get("locations");
		if(locations!=null&&locations.size()>0){
			for(HashMap<String, Object> locationParam : locations){
				ActivityLocationPo activityLocation = new ActivityLocationPo();
				activityLocation.setName((String)locationParam.get("name"));
				activityLocation.setType((Integer)locationParam.get("type"));
				activityLocation.setLongitude((Double)locationParam.get("longitude"));
				activityLocation.setLatitude((Double)locationParam.get("latitude"));
				activityLocation.setDesc((String)locationParam.get("desc"));
				activityLocations.add(activityLocation);
			}
		}
		
		ActivityEntity activityEntity = new ActivityEntity(activityPo,activityLocations);
		return activityEntity;
	}

}
