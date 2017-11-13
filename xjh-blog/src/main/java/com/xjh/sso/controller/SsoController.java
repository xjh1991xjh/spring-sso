package com.xjh.sso.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xjh.sso.constant.SsoConstants;
import com.xjh.sso.session.SsoSession;

/**
 * 单点登录控制器
 * @author xujh
 */
@RestController
public class SsoController {
	
	@Autowired
	private HttpServletRequest request;

	
	/**
	 * 退出登陆
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sso/logout/{token}", method=RequestMethod.GET)
	public String logout(@PathVariable("token") String token){
		String referer = request.getHeader("referer");
		if(StringUtils.isEmpty(token)){
			return "fail";
		}
		if(!StringUtils.isEmpty(referer) && !referer.contains(SsoConstants.SSO_DOMAIN)){
			return "fail";
		}
		SsoSession.delSession(token);
		return "success";
	}
}
