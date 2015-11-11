package com.cmsz.hxj.web.service;

import java.util.List;
import java.util.Map;

import com.cmsz.hxj.web.dao.model.ActivityEnrollPo;
import com.cmsz.hxj.web.dao.model.ActivityLocationPo;
import com.cmsz.hxj.web.dao.model.ActivityPo;
import com.cmsz.hxj.web.dao.model.UserPo;
import com.cmsz.hxj.web.entity.ActivityEntity;
import com.cmsz.hxj.web.util.Page;

public interface ActivityService {

	String createActivity(ActivityEntity activityEntity);

	int activityEnroll(ActivityEnrollPo activityEnrollPo);

	void activityInvite(UserPo inviter, List<UserPo> userPos, ActivityPo activity) throws Exception;

	ActivityPo getActivity(String activityId);

	void quitJoinActivity(ActivityEnrollPo activityEnrollPo);

	void UpdateActivityStatus(ActivityPo activityPo, int status);

	List<ActivityPo> getActivityByPage(Map<String, Object> searchParam,Page page);

	List<UserPo> getActivityEnrolls(String acitivityId);

	List<ActivityLocationPo> getActivityLocations(String activityId);

}
