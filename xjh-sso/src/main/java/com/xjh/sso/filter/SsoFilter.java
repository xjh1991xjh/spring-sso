package com.xjh.sso.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.xjh.sso.constant.SsoConstants;
import com.xjh.sso.util.SsoTokenUtil;

/**
 * 单点登录过滤器
 * 
 * @author xujh
 *
 */
public class SsoFilter implements Filter {

	
	/**
	 * 不拦截的url
	 */
	private String excludeUrls;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		// 判断是否已经有本地会话
		HttpSession session = request.getSession();
	    String token = (String) session.getAttribute(SsoConstants.TOKEN);
	    
		if (StringUtils.isEmpty(token)) {
			// 判断是否需要忽略过滤
			String requestUrl = request.getRequestURI();
			if(isExcludeUrl(requestUrl)){
				filterChain.doFilter(servletRequest, servletResponse);
				return ;
			}
			
			// 不是要忽略的url，且没有token表示未登陆，去登陆
			response.sendRedirect(SsoConstants.SSO_LOGIN_URL);
			return;
		}
		
		// 认证本地会话中的token
		if(!SsoTokenUtil.checkToken(token)){
		   response.sendRedirect(SsoConstants.SSO_LOGIN_URL);
		   return ;	
		}
		

		// 认证通过放行
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
	/**
	 * 判断url是否需要忽略过虑
	 * @param url
	 * @return
	 */
	private boolean isExcludeUrl(String url){
		if(StringUtils.isEmpty(url)){
			return false;
		}
		if(StringUtils.isEmpty(excludeUrls)){
			return false;
		}
		String[] excludeUrlArray = excludeUrls.split(",");
		for(String excludeUrl : excludeUrlArray){
			if(url.contains(excludeUrl) || excludeUrl.contains(url)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		excludeUrls = filterConfig.getInitParameter("excludeUrls");
	}

}
