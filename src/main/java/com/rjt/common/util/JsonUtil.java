package com.rjt.common.util;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;



public class JsonUtil {
	/**
	 * 将json格式化为字符串，然后根据key取值
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static String getJsonValue(String jsonStr,String key) {
		 if(StringUtils.isEmpty(jsonStr) || StringUtils.isEmpty(key)) {
			 return "";
		 } 
		 if(key.indexOf(".")==-1) {
			 if(key.indexOf("[")!=-1) {
				 int num =Integer.parseInt(TextUtil.getSubString(key, "[", "]"));
				 key=key.substring(0, key.indexOf("["));
				return  JSON.parseObject(jsonStr).getJSONArray(key).getString(num);
			 }else {
				 return JSON.parseObject(jsonStr).getString(key);
			 }
			 
		 }else {
			 String[] keys = key.split("\\.");
			 for(int i=0;i<keys.length;i++) {
				 String tempKey=keys[i];
				 if(tempKey.indexOf("[")!=-1) {
					 int num =Integer.parseInt(TextUtil.getSubString(tempKey, "[", "]"));
					 tempKey=tempKey.substring(0, keys[i].indexOf("["));
					 jsonStr=JSON.parseObject(jsonStr).getJSONArray(tempKey).getString(num);
				 }else {
					 jsonStr=JSON.parseObject(jsonStr).getString(tempKey);
				 }
			 }
			return jsonStr;
		 }
		 
	}
	
}
