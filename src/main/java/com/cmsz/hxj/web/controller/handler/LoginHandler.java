package com.cmsz.hxj.web.controller.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmsz.hxj.web.controller.ReturnCode;
import com.cmsz.hxj.web.dao.model.UserInterestPo;
import com.cmsz.hxj.web.dao.model.UserPo;
import com.cmsz.hxj.web.service.UserService;

@Component
public class LoginHandler extends BaseHandler {

	@Autowired
	private UserService userService;

	@Override
	public void handle(HashMap<String, Object> param) {
		if (param.get("phone") == null) {
			this.genErrOutputMapWithCode("param error, need phone", ReturnCode.PARAM_ERROR);
			return;
		}

		if (param.get("verifyCode") == null) {
			this.genErrOutputMapWithCode("param error, need verifyCode", ReturnCode.PARAM_ERROR);
			return;
		}
		String phone = (String) param.get("phone");
		String verifyCode = (String) param.get("verifyCode");
		if (userService.checkVerifyCode(phone, verifyCode) == false) {
			this.genErrOutputMapWithCode("verfiCode error", ReturnCode.LOGIN_FAILED);
			return;
		}
		UserPo po = new UserPo();
		po.setPhone(phone);
		if (param.get("itoken") != null) {
			po.setItoken((String) param.get("itoken"));
		}

		UserPo rpo = userService.login(po);
		if (rpo == null) {
			this.genErrOutputMapWithCode("login failed", ReturnCode.LOGIN_FAILED);
		}

		List<UserInterestPo> list = userService.getUserInterest(rpo);
		List<Integer> inlist = new ArrayList<Integer>();
		if (list != null && list.size() > 0) {
			for (UserInterestPo it : list) {
				inlist.add(it.getInterestId());
			}
		}

		dataMap.put("uid", rpo.getUid());
		dataMap.put("phone", rpo.getPhone());
		dataMap.put("name", rpo.getName());
		dataMap.put("sex", rpo.getSex());
		dataMap.put("avatarURL", rpo.getAvatarURL());
		dataMap.put("avatarId", rpo.getAvatarId());
		dataMap.put("interest", inlist);
		this.genSuccOutputMap();
	}

}
