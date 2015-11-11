package com.cmsz.hxj.web.controller.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.dao.model.ActivityLocationPo;
import com.cmsz.hxj.web.dao.model.ActivityPo;
import com.cmsz.hxj.web.dao.model.UserPo;
import com.cmsz.hxj.web.service.ActivityService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class ShowActivityHandler extends BaseHandler {
	private String[] activityReturnParam = {"activityId","uid","phone","activityName","address","beginTime","endTime",
			"deadlineTime","imgURL","imgId","limitNum","activityType","isOpen","traceType","radius","longitude","latitude","status","locationNum"};

	private String[] locationReturnParam = {"name","type","longitude","latitude","desc"};
	
	private String[] enrollReturnParam = {"uid","phone","name","sex","avatarURL","avatarId"};
	
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
		
		try{
			String activityId = (String)param.get("acitivityID");
			Map<String,Object> activityResultMap = genActivityResultMap(activityId);
			JSONArray enrollsJsonArray = genActivityEnrollsJsonArray(activityId);
			JSONArray locationsJsonArray = genActivityLocationsJsonArray(activityId);
			activityResultMap.put("enrollNum", enrollsJsonArray.size());
			
			dataMap.put("activity", JSONObject.fromObject(activityResultMap));
			dataMap.put("locations", locationsJsonArray);
			dataMap.put("enrolls", enrollsJsonArray);
			this.genSuccOutputMap();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			this.genErrOutputMapWithCode("search failed", ReturnCode.SEARCH_ERROR);
		} catch (SecurityException e) {
			e.printStackTrace();
			this.genErrOutputMapWithCode("search failed", ReturnCode.SEARCH_ERROR);
		}
	}

	private Map<String, Object> genActivityResultMap(String activityId) throws NoSuchMethodException, SecurityException {
		ActivityPo activityPo = activityService.getActivity(activityId);
		Map<String,Object> activityResultMap = new HashMap<String,Object>();
		this.geneDataMap(activityResultMap,activityPo,activityReturnParam);
		return activityResultMap;
	}
	
	private JSONArray genActivityEnrollsJsonArray(String activityId) throws NoSuchMethodException, SecurityException {
		List<UserPo> enrolls = activityService.getActivityEnrolls(activityId);
		JSONArray enrollsJsonArray = new JSONArray();
		if(enrolls!=null && enrolls.size()>0){
			enrollsJsonArray = genJsonArray(enrolls,enrollReturnParam);
		}
		return enrollsJsonArray;
	}
	
	private JSONArray genActivityLocationsJsonArray(String activityId) throws NoSuchMethodException, SecurityException {
		List<ActivityLocationPo> locations = activityService.getActivityLocations(activityId);
		JSONArray locationsJsonArray = new JSONArray();
		if(locations!=null && locations.size()>0){
			locationsJsonArray = genJsonArray(locations,locationReturnParam);
		}
		return locationsJsonArray;
	}
	
	private <T> JSONArray genJsonArray(List<T> list,String[] returnParam) throws NoSuchMethodException, SecurityException {
		JSONArray jsonArray = new JSONArray();
		for(T t : list){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			this.geneDataMap(resultMap,t,returnParam);
			jsonArray.add(JSONObject.fromObject(resultMap));
		}
		return jsonArray;
	}

}
