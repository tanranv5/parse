package com.rjt.video.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.rjt.common.util.JsonUtil;
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
public class PiPiXServiceImpl implements VideoService{

	@Override
	public VideoModel parseUrl(String url) {
		// TODO Auto-generated method stub
		VideoModel videoModel=new VideoModel();
		try {
			Matcher matcher = Pattern.compile("\\d{19}").matcher(url);
			if(matcher.find()) {
				String itemId=matcher.group(0);
				url="https://is.snssdk.com/bds/item/detail/?app_name=super&aid=1319&item_id="+itemId;
				OkHttpClient okHttpClient = new OkHttpClient();
				Request request = new Request.Builder().url(url).build();
				Response response = okHttpClient.newCall(request).execute();
				String result=response.body().string();
				System.out.println(result);
				 videoModel.setName(JsonUtil.getJsonValue(result, "data.data.share.title"));
				 videoModel.setPlayAddr(JsonUtil.getJsonValue(result, "data.data.video.video_fallback.url_list[0].url"));
				 videoModel.setCover(JsonUtil.getJsonValue(result, "data.data.video.video_fallback.cover_image.url_list[0].url"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return videoModel;
	}
	public static void main(String[] args) {
		System.out.println(new PiPiXServiceImpl().parseUrl("https://h5.pipix.com/item/6677539389557446916"));
	}
}
