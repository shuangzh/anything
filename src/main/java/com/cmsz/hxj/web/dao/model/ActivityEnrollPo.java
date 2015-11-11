package com.cmsz.hxj.web.dao.model;

public class ActivityEnrollPo {
	private String activityId;
	private String uid;
	private String phone;
	private String enrollTime;
	private int hasinformed;
	private int joinstatus;
	
	
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEnrollTime() {
		return enrollTime;
	}
	public void setEnrollTime(String enrollTime) {
		this.enrollTime = enrollTime;
	}
	public int getHasinformed() {
		return hasinformed;
	}
	public void setHasinformed(int hasinformed) {
		this.hasinformed = hasinformed;
	}
	public int getJoinstatus() {
		return joinstatus;
	}
	public void setJoinstatus(int joinstatus) {
		this.joinstatus = joinstatus;
	}
	
}
