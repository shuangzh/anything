package com.cmsz.hxj.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.hxj.web.dao.ActivityDao;
import com.cmsz.hxj.web.dao.UserDao;
import com.cmsz.hxj.web.dao.model.ActivityEnrollPo;
import com.cmsz.hxj.web.dao.model.ActivityLocationPo;
import com.cmsz.hxj.web.dao.model.ActivityPo;
import com.cmsz.hxj.web.dao.model.UserPo;
import com.cmsz.hxj.web.entity.ActivityEntity;
import com.cmsz.hxj.web.service.ActivityService;
import com.cmsz.hxj.web.util.Constants;
import com.cmsz.hxj.web.util.DateUtil;
import com.cmsz.hxj.web.util.IdUtil;
import com.cmsz.hxj.web.util.Log;
import com.cmsz.hxj.web.util.Page;
import com.cmsz.hxj.web.util.SmsUtil;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public String createActivity(ActivityEntity activityEntity) {
		String activityId = genIdUnExist();
		ActivityPo activityPo = activityEntity.getActivityPo();
		activityPo.setActivityId(activityId);
		activityPo.setStatus(Constants.ActivityStatus.create);
		activityDao.insertActivity(activityPo);
		List<ActivityLocationPo> activityLocations = activityEntity.getActivityLocations();
		for(ActivityLocationPo activityLocation : activityLocations){
			activityLocation.setActivityId(activityId);
			activityDao.insertActivityLocation(activityLocation);
		}
		return activityId;
	}

	private String genIdUnExist() {
		String id = null;
		boolean flag = false;
		do{
			id = IdUtil.getTimeToMd5().substring(0,10);
			flag = idExist(id);
		}while(flag);
		return id;
	}

	private boolean idExist(String id) {
		int num = activityDao.count(id);
		if(num == 0){
			return false;
		}
		return true;
	}

	@Override
	public int activityEnroll(ActivityEnrollPo activityEnrollPo) {
		int flag = -1;
		String enrollTime = DateUtil.dateToString(new Date(), DateUtil.DEFAULT_DATE_FORMAT);
		List<ActivityPo> activityPos = activityDao.getActivity(activityEnrollPo.getActivityId());
		if(activityPos!=null && activityPos.size()>0){
			ActivityPo activityPo = activityPos.get(0);
			if(activityPo.getStatus() == Constants.ActivityStatus.create){
				activityEnrollPo.setEnrollTime(enrollTime);
				activityEnrollPo.setHasinformed(Constants.EnrollStatus.uninformed);
				activityEnrollPo.setJoinstatus(Constants.EnrollStatus.enroll);
				activityDao.insertActivityEnroll(activityEnrollPo);
			}
			flag = activityPo.getStatus();
		}
		return flag;
	}

	@Override
	public void activityInvite(UserPo inviter,List<UserPo> userPos,ActivityPo activity) throws Exception {
		String smsMessage = genInviteSmsMessage(inviter,activity);
		for(UserPo userPo : userPos){
			List<UserPo> userPoList = userDao.getUsers(userPo.getPhone());
			if(userPoList!=null&&userPoList.size()>0){
				UserPo userPoTemp = userPoList.get(0);
				if(userPoTemp.getItoken()!=null&&userPoTemp.getItoken().length()>0){
					//调用iphone消息机制机制
				}else{
					//调用其它手机的消息机制
				}
			}else{
				sendSms(userPo.getPhone(),smsMessage);
			}
			
		}
		
	}

	private String genInviteSmsMessage(UserPo inviter, ActivityPo activity) {
		StringBuilder smsMessage = new StringBuilder();
		smsMessage.append("邀请参加").append(inviter.getName()).append("发起的").append(activity.getActivityName());
		return smsMessage.toString();
	}

	private void sendSms(String phone,String smsMessage) throws Exception {
		Log.SERVICE.info("给手机" + phone + "发送邀约信息");
		try {
			SmsUtil.sendSms(phone, "【和行记】："+ smsMessage);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("调用短信接口，发送信息失败！");
			throw e;
		}
	}

	@Override
	public ActivityPo getActivity(String activityId) {
		List<ActivityPo> activityPos = activityDao.getActivity(activityId);
		if(activityPos!=null && activityPos.size()>0){
			return activityPos.get(0);
		}
		return null;
	}

	@Override
	public void quitJoinActivity(ActivityEnrollPo activityEnrollPo) {
		activityEnrollPo.setJoinstatus(Constants.EnrollStatus.quit);
		activityDao.updateActivityEnrollStatus(activityEnrollPo);
	}

	@Override
	public void UpdateActivityStatus(ActivityPo activityPo, int status) {
		activityPo.setStatus(status);
		if(status == Constants.ActivityStatus.end){
			ActivityEnrollPo activityEnrollPo = new ActivityEnrollPo();
			activityEnrollPo.setActivityId(activityPo.getActivityId());
			activityEnrollPo.setJoinstatus(Constants.EnrollStatus.end);
			activityDao.updateActivityEnrollStatus(activityEnrollPo);
		}
		activityDao.updateActivityStatus(activityPo);
	}

	@Override
	public List<ActivityPo> getActivityByPage(Map<String, Object> searchParam,Page page) {
		List<ActivityPo> activityPosReturn = new ArrayList<ActivityPo>();
		List<ActivityPo> activityPos = activityDao.getActivityByParams(searchParam);
		if(activityPos!=null && activityPos.size()>0){
			int total = activityPos.size();
			page.setTotalCount(total);
			int pageSize = page.getNumPerPage();
			int start = (page.getPageNum()-1)*pageSize;
			searchParam.put("start", start);
			searchParam.put("pageSize",pageSize);
			activityPosReturn = activityDao.getActivityByParams(searchParam);
		}
		return activityPosReturn;
	}

	@Override
	public List<UserPo> getActivityEnrolls(String acitivityId) {
		List<UserPo> enrolls = activityDao.getActivityEnrolls(acitivityId);
		return enrolls;
	}

	@Override
	public List<ActivityLocationPo> getActivityLocations(String activityId) {
		List<ActivityLocationPo> locations = activityDao.getActivityLocations(activityId);
		return locations;
	}

}
