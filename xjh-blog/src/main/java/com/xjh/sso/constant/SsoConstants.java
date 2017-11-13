package com.xjh.sso.constant;

/**
 * sso的常量
 * @author xujh
 *
 */
public class SsoConstants {
	/**
	 * token的参数key
	 */
	public static String TOKEN = "ssoToken";
	
	/**
	 * SSO的域名
	 */
	public static final String SSO_DOMAIN = "sso.xjh.com";
	/**
	 * org的域名
	 */
	public static final String ORG_DOMAIN = "org.xjh.com";

	/**
	 * SSO的登陆地址
	 */
	public static final String SSO_LOGIN_URL = "http://" + SSO_DOMAIN + "/login/tologin";

	/**
	 * 该子系统的模拟受保护的资源
	 */
	public static final String ORG_INDEX_URL = "http://" + ORG_DOMAIN + "/index?timeStatmp=";
	/**
	 * 校验token的地址
	 */
	public static final String CHECK_TOKEN_URL = "http://" + SSO_DOMAIN + "/sso/token/check/";
	
	/**
	 * blog的域名
	 */
	public static final String BLOG_DOMAIN = "blog.xjh.com";

	/**
	 * 该子系统的模拟受保护的资源
	 */
	public static final String BLOG_INDEX_URL = "http://" + BLOG_DOMAIN + "/index?timeStatmp=";

	
}
