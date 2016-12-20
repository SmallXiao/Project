package com.project.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 解析IP地址为地理位置
 * @author SunXiao
 *
 */
public class IP2Location {

	private static Logger LOG = LogManager.getLogger(IP2Location.class);
	
	// 淘宝的根据ip地址获取地理位的接口
	private static String GET_TAOBAO_IP_INFO = "http://ip.taobao.com/service/getIpInfo.php?ip=%s";
	
	// 新浪的根据ip地址获取地理位的接口
	private static String GET_SINA_IP_INFO = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=%s";

	/**
	 * 根据IP获取地理位置信息（淘宝获取速度很慢）
	 * @param ip
	 * @return
	 */
	public static Map<String, String> getLocationByIP2(String ip) {
		String result = HttpUtil.get(String.format(GET_TAOBAO_IP_INFO, ip));
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (!"0".equals(jsonObject.getString("code"))) {
			LOG.warn("get location by ip error：" + jsonObject.getString("data")+"，ip："+ ip);
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		JSONObject dataObject = jsonObject.getJSONObject("data");
		String country = dataObject.getString("country");
		String region = dataObject.getString("region");
		String city = dataObject.getString("city");
		
		map.put("country", country);
		map.put("region", region);
		map.put("city", city);
		return map;
	}
	
	public static Map<String, String> getLocationByIP(String ip) {
		String result = HttpUtil.get(String.format(GET_SINA_IP_INFO, ip));
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(result);
			String country = jsonObject.getString("country");
			String province = jsonObject.getString("province");
			String city = jsonObject.getString("city");
			
			map.put("country", country);
			map.put("province", province);
			map.put("city", city);
		} catch (Exception e) {
			LOG.warn("get location by ip error ip：" + ip , e);
		}
		return map;
	}
	
	public static void main(String[] args) {
		String ip = "106.114.186.109";
		Map<String, String> map = IP2Location.getLocationByIP(ip);
		System.out.println(map.get("country"));
		System.out.println(map.get("province"));
		System.out.println(map.get("city"));
	}
}
