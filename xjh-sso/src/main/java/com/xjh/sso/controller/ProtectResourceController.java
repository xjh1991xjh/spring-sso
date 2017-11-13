package com.xjh.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProtectResourceController {
	
	@RequestMapping("/index")
	public ModelAndView index(){
		System.out.println("welcome xjh-sso index page");
		return new ModelAndView("sso_index");
	}

}
