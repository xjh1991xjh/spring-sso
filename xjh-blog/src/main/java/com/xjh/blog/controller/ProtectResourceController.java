package com.xjh.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 模拟受保护的资源
 * @author xujh
 *
 */
@Controller
public class ProtectResourceController {
	
	@RequestMapping("/index")
	public ModelAndView index(){
		System.out.println("welcome to xjh-blog index page");
		return new ModelAndView("blog_index");
	}

}
