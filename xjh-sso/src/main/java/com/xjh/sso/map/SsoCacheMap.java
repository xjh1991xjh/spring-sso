package com.xjh.sso.map;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.CollectionUtils;

/**
 * 模拟缓存sso的token的工具
 * @author xujh
 *
 */
public class SsoCacheMap {

	/**
	 * token跟注册系统关系
	 */
	private static final ConcurrentHashMap<String, List<String>> tokenRegisterUrls = new ConcurrentHashMap<>();

	/**
	 * 根据token获取注册token的子系统地址列表
	 * 
	 * @param token
	 */
	public static List<String> getRegistUrl(String token) {
		return tokenRegisterUrls.get(token);
	}
	
	/**
	 * 根据token删除注册token的子系统地址列表
	 * 
	 * @param token
	 */
	public static List<String> delRegistUrl(String token) {
		return tokenRegisterUrls.remove(token);
	}


	/**
	 * token和子系统的注册地址添加到映射表
	 * 
	 * @param token
	 * @param registerUrl
	 */
	public static void addRegistUrl(String token, String registerUrl) {
		List<String> registerUrls = tokenRegisterUrls.get(token);
		if(CollectionUtils.isEmpty(registerUrls)){
			registerUrls = new ArrayList<String>();
			tokenRegisterUrls.put(token, registerUrls);
		}
		registerUrls.add(registerUrl);
	}
}
