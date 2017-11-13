package com.xjh.sso.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.xjh.sso.constant.SsoConstants;
import com.xjh.sso.map.SsoCacheMap;
import com.xjh.sso.util.DomainUtil;

@Controller
public class LoginController {
	@Autowired
	private HttpServletRequest request;

	/**
	 * 执行登陆
	 * @param username
	 * @param password
	 * @param reqUrl
	 * @return
	 */
	@RequestMapping(value = "/login/dologin", method = RequestMethod.POST)
	public ModelAndView dologin(@RequestParam("username") String username, 
			@RequestParam("password") String password,
			@RequestParam("reqUrl") String reqUrl) {
		HttpSession session = request.getSession();
		String token = UUID.randomUUID().toString();
		session.setAttribute(SsoConstants.TOKEN, token);
		String domain = DomainUtil.getDomain(reqUrl);
		reqUrl = reqUrl + "&" + SsoConstants.TOKEN + "=" + token;
		SsoCacheMap.addRegistUrl(token,  domain + "/sso/logout/");
		return new ModelAndView(new RedirectView(reqUrl));
	}
	
	/**
	 * 跳转到登录页
	 * @param reqUrl
	 * @return
	 */
	@RequestMapping(value = "/login/tologin")
	public ModelAndView tologin(@RequestParam(value="reqUrl", defaultValue="http://org.xjh.com/index?timeStamp=123456") String reqUrl) {
		// 判断是否已经有本地会话
		HttpSession session = request.getSession();
	    String token = (String) session.getAttribute(SsoConstants.TOKEN);
	    if(!StringUtils.isEmpty(token)){ //如果已经登陆，重定向回去
	    	String domain = DomainUtil.getDomain(reqUrl);
			reqUrl = reqUrl + "&" + SsoConstants.TOKEN + "=" + token;
			SsoCacheMap.addRegistUrl(token,  domain + "/sso/logout");
			return new ModelAndView(new RedirectView(reqUrl));
	    }
		request.setAttribute("reqUrl", reqUrl);
		return new ModelAndView("login");
	}

	/**
	 * 退出登陆
	 * @param reqUrl
	 * @return
	 */
	@RequestMapping(value = "/login/dologout", method = RequestMethod.GET)
	public ModelAndView dologout() {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String token = (String) session.getAttribute(SsoConstants.TOKEN);
			if(!StringUtils.isEmpty(token)){
				session.invalidate();
			}
		}
		return new ModelAndView("logout_success");
	}

}
