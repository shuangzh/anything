package com.cmsz.hxj.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.hxj.web.dao.UserDao;
import com.cmsz.hxj.web.dao.model.UserInterestPo;
import com.cmsz.hxj.web.dao.model.UserPo;
import com.cmsz.hxj.web.service.UserService;
import com.cmsz.hxj.web.util.IdUtil;
import com.cmsz.hxj.web.util.Log;
import com.cmsz.hxj.web.util.SmsUtil;

@Service
public class UserServiceImpl implements UserService {

	private Map<String, String> holder = new HashMap<String, String>();

	@Autowired
	private UserDao userDao;

	@Override
	public void sendVerifyCode(String phone){
		String code = IdUtil.genVerifyCode();
		holder.put(phone, code);
		Log.SERVICE.info("给手机" + phone + "发送登录验证码" + code);
		try {
			SmsUtil.sendSms(phone, "【和行记】登录验证码："+code);
		} catch (Exception e) {
			e.printStackTrace();
			Log.SERVICE.error("调用短信接口，发送验证码失败！");
		}
	}

	@Override
	public UserPo login(UserPo po) {
		List<UserPo> list = userDao.getUsers(po.getPhone());
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			String uid = genIdUnExist();
			po.setUid(uid);
			userDao.saveUser(po);
			return po;
		}
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

	private boolean idExist(String uid) {
		int num = userDao.count(uid);
		if(num == 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean checkVerifyCode(String phone, String VerifyCode) {

		// 测试时使用
		if (VerifyCode.compareTo("0000") == 0)
			return true;

		if (holder.get(phone) == null)
			return false;

		if (VerifyCode.compareTo(holder.get(phone)) == 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<UserInterestPo> getUserInterest(UserPo po) {
		return userDao.getUserInterest(po);
	}

	@Override
	public int insertUserInterest(UserInterestPo po) {
		userDao.insertUserInterest(po);
		return 0;
	}

	@Override
	public int updateUserInfo(UserPo po) {
		userDao.updateUserInfo(po);
		return 0;
	}

	@Override
	public int deleteUserInterest(UserPo po) {
		userDao.deleteUserInterest(po);
		return 0;
	}

	@Override
	public List<UserPo> getUsers(List<String> phones) {
		List<UserPo> userPoList = new ArrayList<UserPo>();
		if(phones!=null){
			for(String phone : phones){
				List<UserPo> list = userDao.getUsers(phone);
				if (list != null && list.size() > 0) {
					userPoList.add(list.get(0));
				}
			}
			return userPoList;
		}else{
			return userPoList;
		}
	}

	@Override
	public UserPo getUser(String phone) {
		List<UserPo> list = userDao.getUsers(phone);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
