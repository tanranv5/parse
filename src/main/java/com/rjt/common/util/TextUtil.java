package com.rjt.common.util;

import blade.kit.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;


public class TextUtil {
	
	private static Logger logger = LoggerFactory.getLogger(TextUtil.class);
	/**
	 * 取两个文本之间的文本值
	 * 
	 * @param text
	 * @param left
	 * @param right
	 * @return
	 */
	public static String getSubString(String text, String left, String right) {
		String result = "";
		int zLen;
		if (left == null || left.isEmpty()) {
			zLen = 0;
		} else {
			zLen = text.indexOf(left);
			if (zLen > -1) {
				zLen += left.length();
			} else {
				zLen = 0;
			}
		}
		int yLen = text.indexOf(right, zLen);
		if (yLen < 0 || right == null || right.isEmpty()) {
			yLen = text.length();
		}
		result = text.substring(zLen, yLen);
		return result;
	}

	/**
	 * 获取ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
	    
	     String ip = request.getHeader("x-forwarded-for"); 
       if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
           // 多次反向代理后会有多个ip值，第一个ip才是真实ip
           if( ip.indexOf(",")!=-1 ){
               ip = ip.split(",")[0];
           }
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("Proxy-Client-IP");  
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("WL-Proxy-Client-IP");  
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("HTTP_CLIENT_IP");  
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("X-Real-IP");  
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getRemoteAddr();  
       } 
       return ip;  
   }

	/**
	 * 根据ip获取地址
	 * 
	 * @param
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getPlace(String ip) throws UnsupportedEncodingException {
		if (ip.indexOf(", ") != -1) {
			ip = ip.split(", ")[1];
		}
		String url = "https://ip.cn/index.php?ip=" + ip;
		HttpRequest request = HttpRequest.get(url, true).header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");
		String res = request.body();
		String place = TextUtil.getSubString(res, "置：<code>", "</code></p><p>");
		request.disconnect();
		return place;
	}

	/**
	 * 根据地址获取纬度
	 * 
	 * @param
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getWd(String place) throws UnsupportedEncodingException {
		String url = "http://apis.map.qq.com/jsapi?qt=poi&wd=" + URLEncoder.encode(place, "utf8");
		HttpRequest request = HttpRequest.get(url, true);
		String res = request.body();
		request.disconnect();
		return handResult(res);
	}

	/**
	 * 处理数据
	 * 
	 * @param res
	 * @return
	 */
	private static String handResult(String res) {
		JSONObject json = JSON.parseObject(res);
		json = JSON.parseObject(json.get("detail").toString());
		JSONArray jsonArray = JSON.parseArray((json.get("pois").toString()));
		int ranNum = getRandomNumRang(jsonArray.size());
		json = JSON.parseObject(jsonArray.get(ranNum).toString());
		return json.get("pointx") + "," + json.get("pointy");
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		int a=0;
		for (int i=0;i<100;i++){
			String ee= getRandomNum(1);
			if(ee.equals("8")){
				System.out.println(a++);
			}
		}
	}
	/**
	 * 根据ip获取维度和精度
	 * @param
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getWd(HttpServletRequest request) throws UnsupportedEncodingException {
		String remortIP = getRemortIP(request);
		logger.info("remortIP 为{}",remortIP);
		String place = getPlace(remortIP);
		logger.info("place 为{}",place);
		String wd="112.537170,37.874690";
		try {
			wd = getWd(place);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e 为{}",e);
		}
		
		logger.info("wd 为{}",wd);
		return wd;
	}
	/**
	 * 取随机数字
	 * 
	 * @param num
	 * @return
	 */
	public static String getRandomNum(int num) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			Random rnd = new Random();
			sb.append(rnd.nextInt(9));
		}
		return sb.toString();
	}

	/**
	 * 根据范围取随机数字
	 * 
	 * @param
	 * @return
	 */
	public static int getRandomNumRang(int end) {
		Random rnd = new Random();
		return rnd.nextInt(end);
	}

	
	
	public static String makeImei () {
		String imeiString=TextUtil.getRandomNum(14);
		char[] imeiChar=imeiString.toCharArray();
		int resultInt=0;
		for (int i = 0; i < imeiChar.length; i++) {
			int a=Integer.parseInt(String.valueOf(imeiChar[i]));
			i++;
			final int temp=Integer.parseInt(String.valueOf(imeiChar[i]))*2;
			final int b=temp<10?temp:temp-9;
			resultInt+=a+b;
		}
		resultInt%=10;
		resultInt=resultInt==0?0:10-resultInt;
		return imeiString+resultInt;
	}
}
