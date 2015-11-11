package com.cmsz.hxj.web.controller.handler;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.dao.model.UserInterestPo;
import com.cmsz.hxj.web.dao.model.UserPo;
import com.cmsz.hxj.web.service.UserService;


@Component
public class UpdateUserInfoHandler extends BaseHandler {
	
	@Autowired
	private UserService userService;
	
	@Override
	public void handle(HashMap<String, Object> param) {
		UserPo userPo=new UserPo();
		if(param.get("uid")==null){
			this.genErrOutputMapWithCode("param error, need uid", ReturnCode.PARAM_ERROR);
			return ;
		}
		if(param.get("phone")==null){
			this.genErrOutputMapWithCode("param error, need phone", ReturnCode.PARAM_ERROR);
			return ;
		}
		String uid=(String)param.get("uid");
		String phone=(String)param.get("phone");
		userPo.setUid(uid);
		userPo.setPhone(phone);
		userPo.setSex((Integer)param.get("sex"));
		userPo.setAvatarURL((String)param.get("avatarURL"));
		userPo.setName((String)param.get("name"));
		userPo.setAvatarId((String)param.get("avatarId"));
		userService.updateUserInfo(userPo);
		List<Integer> list=null;
		if(param.get("interest")!=null)
		{
			list=(List<Integer>) param.get("interest");
			if(list!=null && list.size()> 0)
			{
				userService.deleteUserInterest(userPo);
				for(Integer i: list){
					UserInterestPo uipo=new UserInterestPo();
					uipo.setInterestId(i);
					uipo.setPhone(userPo.getPhone());
					uipo.setUid(userPo.getUid());
					userService.insertUserInterest(uipo);
				}
			}
		}
		this.genSuccOutputMap();
	}
}
