package com.cmsz.hxj.web.entity;

import java.util.List;

import com.cmsz.hxj.web.dao.model.ActivityLocationPo;
import com.cmsz.hxj.web.dao.model.ActivityPo;

public class ActivityEntity {
	private ActivityPo activityPo;
	private List<ActivityLocationPo> activityLocations;
	
	public ActivityEntity(){}
	
	public ActivityEntity(ActivityPo activityPo,List<ActivityLocationPo> activityLocations){
		this.activityPo = activityPo;
		this.activityLocations = activityLocations;
	}
	public ActivityPo getActivityPo() {
		return activityPo;
	}
	public void setActivityPo(ActivityPo activityPo) {
		this.activityPo = activityPo;
	}
	public List<ActivityLocationPo> getActivityLocations() {
		return activityLocations;
	}
	public void setActivityLocations(List<ActivityLocationPo> activityLocations) {
		this.activityLocations = activityLocations;
	}
	
	
}
