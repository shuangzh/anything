package com.cmsz.hxj.web.dao.model;

public class UserInterestPo {
	
//	  `id`	int(10) not null auto_increment,
//	  `interestid` int(10) NOT NULL,
//	  `uid` varchar(20) NOT NULL,
//	  `phone` varchar(20) NOT NULL,
	  
	private String id;
	private int interestId;
	private String uid;
	private String phone;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getInterestId() {
		return interestId;
	}
	public void setInterestId(int interestId) {
		this.interestId = interestId;
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
}
