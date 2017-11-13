package com.xjh.sso.util;

import java.io.IOException;

/**
 * sso的token工具类
 * @author xujh
 *
 */
public class SsoTokenUtil {
	/**
	 * 校验token有效性
	 * @param token
	 * @return true有效 false无效
	 */
	public static boolean checkToken(String token) throws IOException {
		//默认是有效未过期，真实场景要验证有效性和是否过期
		return true;
	}
}
