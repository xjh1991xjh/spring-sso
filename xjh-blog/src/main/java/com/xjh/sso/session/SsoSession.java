package com.xjh.sso.session;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

/**
 * sso的token跟回话关系
 * @author xujh
 *
 */
public class SsoSession {
	private static HashMap<String, HttpSession> ssoSessionMap = new HashMap<>();

	/**
	 * 增加会话
	 * @param token
	 * @param session
	 */
	public static synchronized void addSession(String token, HttpSession session) {
		if (session != null && !StringUtils.isEmpty(token)) {
			ssoSessionMap.put(token, session);
		}
	}

	/**
	 * 删除会话
	 * @param token
	 */
	public static synchronized void delSession(String token) {
		if (!StringUtils.isEmpty(token)) {
			HttpSession session = ssoSessionMap.remove(token);
			session.removeAttribute(token);
			session.invalidate();
		}
	}

	/**
	 * 获取会话
	 * @param token
	 * @return
	 */
	public static synchronized HttpSession getSession(String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		return (HttpSession) ssoSessionMap.get(token);
	}
}
