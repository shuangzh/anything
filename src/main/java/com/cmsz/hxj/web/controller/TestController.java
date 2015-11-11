package com.cmsz.hxj.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hh")
public class TestController extends BaseController {
		
	@RequestMapping("/tt")
	public ModelAndView test(HttpSession session,HttpServletRequest request, Map<String,Object> inputMap)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("message", "mvc message");
		mv.addObject("ch","中文");
		System.out.println("xxxxxx999999999999999999999999999999999");
		mv.setViewName("test");
		return mv;
	}
	
	@RequestMapping("/t2")
	public ModelAndView test2(HttpSession session,HttpServletRequest request, @RequestBody Map<String,Object> inputMap)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("message", "mvc message");
		mv.addObject("ch","中文");
		mv.addObject("inputMap", inputMap);
		System.out.println("t2--------------");
		mv.setViewName("test");
		return mv;
	}
	
	@RequestMapping("/t3")
	public Map<String,Object> test3(HttpSession session,HttpServletRequest request, @RequestBody Map<String,Object> inputMap)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("message", "mvc message");
		mv.addObject("ch","中文");
		mv.addObject("inputMap", inputMap);
		System.out.println("t3----------------");
		mv.setViewName("test");
		
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("xx", "xeixt中文");
		result.put("array", new String[]{"数组1", "联2"});
		result.put("array2", new Integer[]{1, 2, 3, 5, 6});
		return result;
	}


}
