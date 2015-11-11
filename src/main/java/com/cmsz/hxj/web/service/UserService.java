package com.cmsz.hxj.web.service;

import java.util.List;

import com.cmsz.hxj.web.dao.model.UserInterestPo;

import com.cmsz.hxj.web.dao.model.UserPo;

public interface UserService {
	
	void sendVerifyCode(String phone) throws Exception;
	boolean checkVerifyCode(String phone, String VerifyCode);
	UserPo login(UserPo po);
	List<UserInterestPo> getUserInterest(UserPo po);
	int insertUserInterest(UserInterestPo po);
	int updateUserInfo(UserPo po);
	int deleteUserInterest(UserPo po);
	List<UserPo> getUsers(List<String> phones);
	UserPo getUser(String uid);
	
}
