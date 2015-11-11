package com.cmsz.hxj.web.dao;

import java.util.List;

import com.cmsz.hxj.web.dao.model.UserInterestPo;
import com.cmsz.hxj.web.dao.model.UserPo;

public interface UserDao {
	public List<UserPo> getAll();

	public void saveUser(UserPo userPo);
	
	public List<UserPo> getUsers(UserPo po);

	public List<UserPo> getUsers(String phone);

	public void updateUser(UserPo userPo);

	public void deleteUserIntrest(String uid);

	public List<Integer> getUserIntrests(String uid);

	public void updateUserToken(UserPo userPo);

	public int count(String uid);

	public List<UserInterestPo> getUserInterest(UserPo po);
	
	public int updateUserInfo(UserPo po);
	
	public int insertUserInterest(UserInterestPo po);
	
	public int deleteUserInterest(UserPo po);
	
}
