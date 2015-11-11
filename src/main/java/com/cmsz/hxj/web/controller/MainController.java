package com.cmsz.hxj.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmsz.hxj.web.controller.handler.ActivityInviteHandler;
import com.cmsz.hxj.web.controller.handler.ActivityQuitHandler;
import com.cmsz.hxj.web.controller.handler.BaseHandler;
import com.cmsz.hxj.web.controller.handler.CheckHandler;
import com.cmsz.hxj.web.controller.handler.CheckVerifyCodeHandler;
import com.cmsz.hxj.web.controller.handler.CreateActivityHandler;
import com.cmsz.hxj.web.controller.handler.EnrollHandler;
import com.cmsz.hxj.web.controller.handler.GetActivityhandler;
import com.cmsz.hxj.web.controller.handler.GetUserInfoHandler;
import com.cmsz.hxj.web.controller.handler.LoginHandler;
import com.cmsz.hxj.web.controller.handler.RegisterHandler;
import com.cmsz.hxj.web.controller.handler.SendVerifyCodeHandler;
import com.cmsz.hxj.web.controller.handler.ShowActivityHandler;
import com.cmsz.hxj.web.controller.handler.UpdateActivityStatusHandler;
import com.cmsz.hxj.web.controller.handler.UpdateUserInfoHandler;
import com.cmsz.hxj.web.util.Log;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
	
	@Autowired
	private RegisterHandler registerHandler;
	@Autowired
	private LoginHandler loginHandler;
	@Autowired
	private SendVerifyCodeHandler sendVerifyCodeHandler;
	@Autowired
	private CheckVerifyCodeHandler checkVerifyCodeHandler;
	@Autowired
	private GetUserInfoHandler getUserInfoHandler;
	@Autowired
	private UpdateUserInfoHandler updateUserInfoHandler;
	@Autowired
	private CheckHandler checkHandler;
	@Autowired
	private CreateActivityHandler createActivityHandler;
	@Autowired
	private EnrollHandler enrollHandler;
	@Autowired
	private ActivityInviteHandler activityInviteHandler;
	@Autowired
	private ActivityQuitHandler activityQuitHandler;
	@Autowired
	private UpdateActivityStatusHandler updateActivityStatusHandler;
	@Autowired
	private GetActivityhandler getActivityhandler;
	@Autowired
	private ShowActivityHandler showActivityHandler;
	
	
	private static HashMap<String, BaseHandler> interfaceMap = null;
	
	protected void initInterfaceMap() {
        if (interfaceMap == null) {
            interfaceMap = new HashMap<String, BaseHandler>();
            interfaceMap.put("login", loginHandler);
            interfaceMap.put("register", registerHandler);
            interfaceMap.put("sendVerifyCode", sendVerifyCodeHandler);
            interfaceMap.put("checkVerifyCode", checkVerifyCodeHandler);
            interfaceMap.put("getUserInfo", getUserInfoHandler);
            interfaceMap.put("updateUserInfo", updateUserInfoHandler);
            interfaceMap.put("check", checkHandler);
            interfaceMap.put("createActivity", createActivityHandler);
            interfaceMap.put("enroll", enrollHandler);
            interfaceMap.put("invite", activityInviteHandler);
            interfaceMap.put("quit", activityQuitHandler);
            interfaceMap.put("updateActivityStatus", updateActivityStatusHandler);
            interfaceMap.put("getActivity", getActivityhandler);
            interfaceMap.put("showActivity", showActivityHandler);
        }
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/index")
	public Map<String, Object> index(HttpSession session,HttpServletRequest request, @RequestBody Map<String,Object> inputMap)
	{
		Map<String, Object> outputMap = new HashMap<String, Object>();
		if (!inputMap.containsKey("interface") || !inputMap.containsKey("param")) {
            genErrOutputMap(outputMap, "param error");
            Log.SERVICE.error("no interface or param");
            return outputMap;
        }
        
        initInterfaceMap();
        
        try {
            String keyInterface = (String)inputMap.get("interface");
            HashMap<String, Object> keyParam = (HashMap<String, Object>)(inputMap.get("param"));
            BaseHandler handler = interfaceMap.get(keyInterface);
            if (handler == null) {
                Log.SERVICE.error("no handler for " + keyInterface);
                genErrOutputMap(outputMap, "interface not exists");
            } else {
            	HashMap<String, Object> resultMap = handler.InvokeHandle(keyParam);
                Log.SERVICE.info(resultMap.toString());
                int code = (int)resultMap.get("code");
                String msg = (String)resultMap.get("msg");
                Object data = (Object)resultMap.get("data");
                if (code == ReturnCode.OK) {
                    genSuccOutputMap(outputMap, data);
                } else {
                    genErrOutputMapWithCode(outputMap, msg, code);
                }
            }
        } catch (Exception e) {
            Log.SERVICE.error(e.getMessage());
            genErrOutputMap(outputMap, "param error");
        }
		return outputMap;
	}
}
