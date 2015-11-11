package com.cmsz.hxj.web.dao;

import java.util.List;
import java.util.Map;

import com.cmsz.hxj.web.dao.model.ActivityEnrollPo;
import com.cmsz.hxj.web.dao.model.ActivityLocationPo;
import com.cmsz.hxj.web.dao.model.ActivityPo;
import com.cmsz.hxj.web.dao.model.UserPo;

public interface ActivityDao {
	int count(String activityId);

	void insertActivity(ActivityPo activityPo);

	void insertActivityLocation(ActivityLocationPo activityLocation);

	void insertActivityEnroll(ActivityEnrollPo activityEnrollPo);

	List<ActivityPo> getActivity(String activityId);

	void updateActivityEnrollStatus(ActivityEnrollPo activityEnrollPo);

	void updateActivityStatus(ActivityPo activityPo);

	List<ActivityPo> getActivityByParams(Map<String, Object> searchParam);

	List<UserPo> getActivityEnrolls(String acitivityId);

	List<ActivityLocationPo> getActivityLocations(String activityId);
}