package com.rjt.video.service.impl;

import org.springframework.stereotype.Service;

import com.rjt.common.util.JsonUtil;
import com.rjt.common.util.TextUtil;
import com.rjt.video.model.VideoModel;
import com.rjt.video.service.VideoService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**  
* @comment
* @author tanran 
* @date 2019年6月14日  
* @version 1.0  
*/
@Service
public class HuoShanServiceImpl implements VideoService {

	@Override
	public VideoModel parseUrl(String url) {
		// TODO Auto-generated method stub
		VideoModel videoModel=new VideoModel();
		/*HttpRequest request = HttpRequest.get(url);
		String res = request.body();
		System.out.println(res);*/
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			Request request = new Request.Builder().url(url).build();
			Response response = okHttpClient.newCall(request).execute();
			String result=response.body().string();
			System.out.println(result);
			result=TextUtil.getSubString(result, "create({d:", "});");
			String videoId=JsonUtil.getJsonValue(result, "video.uri");
			videoModel.setPlayAddr("http://hotsoon.snssdk.com/hotsoon/item/video/_playback/?video_id="+videoId);
			videoModel.setCover(JsonUtil.getJsonValue(result, "video.cover.url_list[0]"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return videoModel;
	}
	
	public static void main(String[] args) {
		System.out.println(new HuoShanServiceImpl().parseUrl("https://reflow.huoshan.com/hotsoon/s/8GCQYDKx700/"));
	}
}
