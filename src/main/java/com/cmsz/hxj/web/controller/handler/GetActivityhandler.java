package com.cmsz.hxj.web.controller.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.dao.model.ActivityPo;
import com.cmsz.hxj.web.service.ActivityService;
import com.cmsz.hxj.web.util.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class GetActivityhandler extends BaseHandler {
	private String[] returnParam = {"activityId","uid","phone","activityName","address","beginTime","endTime",
			"deadlineTime","imgURL","limitNum","activityType","status"};

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
		Map<String,Object> searchParam = genSearchParam(param);
		Page page = genPageParam(param);
		List<ActivityPo> activityPos = activityService.getActivityByPage(searchParam,page);
		JSONArray jsonArray = new JSONArray();
		try {
			for(ActivityPo activityPo:activityPos){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				this.geneDataMap(resultMap,activityPo,returnParam);
				jsonArray.add(JSONObject.fromObject(resultMap));
			}
			dataMap.put("totalPage", page.getTotalPage());
			dataMap.put("totalNum", page.getTotalCount());
			dataMap.put("pageNum", page.getPageNum());
			dataMap.put("activity", jsonArray);
			this.genSuccOutputMap();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			this.genErrOutputMapWithCode("search failed", ReturnCode.SEARCH_ERROR);
		} catch (SecurityException e) {
			e.printStackTrace();
			this.genErrOutputMapWithCode("search failed", ReturnCode.SEARCH_ERROR);
		}
	}

	private Page genPageParam(HashMap<String, Object> param) {
		Page page = new Page();
		page.setPageNum((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageCount"));
		return page;
	}

	private Map<String, Object> genSearchParam(HashMap<String, Object> param) {
		Map<String, Object> searchParam = new HashMap<String, Object>();
		searchParam.put("province", (String)param.get("province"));
		searchParam.put("city", (String)param.get("city"));
		searchParam.put("status", (List<Integer>)param.get("status"));
		searchParam.put("activityType", (List<Integer>)param.get("activityType"));
		return searchParam;
	}

}
