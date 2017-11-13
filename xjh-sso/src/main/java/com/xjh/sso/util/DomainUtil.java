package com.xjh.sso.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 域名工具类
 * @author xujh
 *
 */
public class DomainUtil {
	
	/**
	 * 获取协议和域名部分
	 * 
	 * @param url 地址
	 * @return
	 */
	public static String getDomain(String url) {
		if(url == null){
			return url;
		}
		// 使用正则表达式过滤，
		String domainReg = "((http|ftp|https)://)(([a-zA-Z0-9._-]+)|([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}))(([a-zA-Z]{2,6})|(:[0-9]{1,4})?)";
		String domain = "";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(domainReg);
		// 忽略大小写的写法
		Matcher matcher = pattern.matcher(url);
		// 若url==http://127.0.0.1:9040或www.baidu.com的，正则表达式表示匹配
		if (matcher.matches()) {
			domain = url;
		} else {
			String[] urlParts = url.split(domainReg);
			if (urlParts.length > 1) {
				String substring = url.substring(0, url.length() - urlParts[1].length());
				domain = substring;
			} else {
				domain = urlParts[0];
			}
		}
		return domain;
	}

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getDomain("http://www.baidu.com/system/verList"));
		System.out.println(getDomain("http://WWW.BAIDU.COM/TESE/verList"));
		System.out.println(getDomain("http://blog.csdn.net/weasleyqi/article/details/7912647"));
		System.out.println(getDomain("https://segmentfault.com/q/1010000000703645"));
		System.out.println(getDomain("http://www.cnblogs.com/afarmer/archive/2011/08/29/2158860.html"));
		System.out.println(getDomain("http://127.0.0.1/123/1"));
		System.out.println("1:" + getDomain("http://127.0.0.1:9040/system/verList"));
		System.out.println("2:" + getDomain(
				"http://127.0.0.1:9040/system/verList?loginName=1&password=AD07FB25AA2D3A9F96EE12F25E0BE902"));
		System.out.println("3:" + getDomain("http://127.0.0.1:9040/"));
	}
}
