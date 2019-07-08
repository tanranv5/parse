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
public class KuaiShouServiceImpl implements VideoService {

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
			String photoId=TextUtil.getSubString(result, "\\\"photoId\\\":\\\"", "\\\"");
			System.out.println(photoId);
			url="https://api.kmovie.gifshow.com/rest/n/kmovie/app/photo/getPhotoById?WS&jjh_yqc&ws&photoId="+photoId;
			request=new Request.Builder().url(url).build();
			response=okHttpClient.newCall(request).execute();
			result=response.body().string();
			 System.out.println(result);
			 videoModel.setName(JsonUtil.getJsonValue(result, "photo.caption"));
			 videoModel.setPlayAddr(JsonUtil.getJsonValue(result, "photo.mainUrl"));
			 videoModel.setCover(JsonUtil.getJsonValue(result, "photo.coverUrl"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return videoModel;
	}
	
	public static void main(String[] args) {
		System.out.println(new KuaiShouServiceImpl().parseUrl("http://m.chenzhongtech.com/s/mhn5haAq/"));
	}
}
