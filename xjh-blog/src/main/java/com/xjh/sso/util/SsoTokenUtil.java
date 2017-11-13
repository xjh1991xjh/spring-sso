package com.xjh.sso.util;

import java.io.IOException;

import org.springframework.web.client.RestTemplate;

import com.xjh.sso.constant.SsoConstants;

/**
 * sso的token工具类
 * @author xujh
 *
 */
public class SsoTokenUtil {
	/**
	 * 校验token有效性
	 * @param token
	 * @param redirectSsoUrl
	 * @throws IOException
	 * @return true有效 false无效
	 */
	public static boolean checkToken(String token) throws IOException {
		// 调用sso进行token验证
		RestTemplate restTempate = new RestTemplate();
		String checkResult = restTempate.getForObject(SsoConstants.CHECK_TOKEN_URL + token, String.class);
		if ("1".equals(checkResult)) {
			return false;
		}
		return true;
	}

}
