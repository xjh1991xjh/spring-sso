package com.xjh.sso.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xjh.sso.map.SsoCacheMap;

@RestController
public class SsoController {

	/**
	 * 验证token有效性
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/sso/token/check/{token}", method = RequestMethod.GET)
	public String checkToken(@PathVariable("token") String token) {
		if(StringUtils.isEmpty(token)){
			return "1";
		}
		//验证token是否存在和有效性
		List<String> registerUrlList = SsoCacheMap.getRegistUrl(token);
		if(CollectionUtils.isEmpty(registerUrlList)){
			return "1";
		}
		return "0";
	}

}
