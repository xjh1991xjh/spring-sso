package com.xjh.org.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProtectResourceController {
	
	@RequestMapping("/index")
	public ModelAndView index(){
		System.out.println("welcome xjh-org index page");
		return new ModelAndView("org_index");
	}

}
