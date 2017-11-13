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
import com.xjh.sso.session.SsoSession;
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

		// 判断是否需要忽略过滤
		String requestUrl = request.getRequestURI();
		if (isExcludeUrl(requestUrl)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		// 重定向到sso的url
		String redirectSsoUrl = SsoConstants.SSO_LOGIN_URL + "?reqUrl=" + SsoConstants.ORG_INDEX_URL + System.currentTimeMillis();

		// 如果是从sso跳回来的
		String refererUrl = request.getHeader("referer");
		String token = request.getParameter(SsoConstants.TOKEN);

		// 验证sso返回的token
		if (!StringUtils.isEmpty(token)) {
			if ((refererUrl != null && !refererUrl.contains(SsoConstants.SSO_DOMAIN))) { // 如果是非sso域过来的
				response.sendRedirect(redirectSsoUrl);
				return;
			}
			if (!SsoTokenUtil.checkToken(token)) {
				response.sendRedirect(redirectSsoUrl);
				return;
			} else {
				// 认证通过放行
				HttpSession session = request.getSession();
				session.setAttribute(SsoConstants.TOKEN, token);
				// 加入会话集合，以便注销移除
				if (SsoSession.getSession(token) == null) {
					SsoSession.addSession(token, session);
				}
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		}

		// 判断是否已经有本地会话
		HttpSession session = request.getSession();
		token = (String) session.getAttribute(SsoConstants.TOKEN);
		if (StringUtils.isEmpty(token)) {
			// 没有token表示未登陆，去登陆
			response.sendRedirect(redirectSsoUrl);
			return;
		}

		// 认证本地会话中的token
		if (!SsoTokenUtil.checkToken(token)) {
			response.sendRedirect(redirectSsoUrl);
			return;
		}

		// 加入会话集合，以便注销移除
		if (SsoSession.getSession(token) == null) {
			SsoSession.addSession(token, session);
		}

		// 认证通过放行
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 判断url是否需要忽略过虑
	 * 
	 * @param url
	 * @return
	 */
	private boolean isExcludeUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return false;
		}
		if (StringUtils.isEmpty(excludeUrls)) {
			return false;
		}
		String[] excludeUrlArray = excludeUrls.split(",");
		for (String excludeUrl : excludeUrlArray) {
			if (url.contains(excludeUrl) || excludeUrl.contains(url)) {
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
