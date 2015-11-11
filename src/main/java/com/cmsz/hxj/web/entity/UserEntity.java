package com.cmsz.hxj.web.entity;

import java.util.List;

import com.cmsz.hxj.web.dao.model.UserInterestPo;
import com.cmsz.hxj.web.dao.model.UserPo;

public class UserEntity {
	private UserPo userPo;
	private List<UserInterestPo> userInterestPos;
	
	public UserEntity(){}
	
	public UserEntity(UserPo userPo,List<UserInterestPo> userInterestPos){
		this.userPo = userPo;
		this.userInterestPos = userInterestPos;
	}
	
	public UserPo getUserPo() {
		return userPo;
	}

	public void setUserPo(UserPo userPo) {
		this.userPo = userPo;
	}

	public List<UserInterestPo> getUserInterestPos() {
		return userInterestPos;
	}

	public void setUserInterestPos(List<UserInterestPo> userInterestPos) {
		this.userInterestPos = userInterestPos;
	}

}
