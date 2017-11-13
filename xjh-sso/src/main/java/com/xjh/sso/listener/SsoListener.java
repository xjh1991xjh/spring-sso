package com.xjh.sso.listener;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.xjh.sso.constant.SsoConstants;
import com.xjh.sso.map.SsoCacheMap;

/**
 * sso会话监听器
 * @author xujh
 *
 */
public class SsoListener implements HttpSessionListener  {
	
	private static final Logger logger = Logger.getLogger(SsoListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		
		//获取当前会话中的token
		String token = (String)session.getAttribute(SsoConstants.TOKEN);
		if(StringUtils.isEmpty(token)){
			return ;
		}
		
		//获取token注册的子系统
		List<String> registerUrls = SsoCacheMap.getRegistUrl(token);
		if(CollectionUtils.isEmpty(registerUrls)){
			return ;
		}
		
		RestTemplate restTemplate = new RestTemplate();
		
		//向所有注册系统发送注销请求
		for(String url : registerUrls ){
			String loginoutUrl = url + token;
			String result = null;
			try {
				result = restTemplate.getForObject(loginoutUrl, String.class);
			} catch (Exception e) {
			}
			if("success".equals(result)){
				SsoCacheMap.delRegistUrl(token); //去除子系统和token
				logger.info("logout '" + loginoutUrl + "' success!" );
			}else{
				logger.warn("logout '" + loginoutUrl + "' success!" );
			}
		}
	}

}
