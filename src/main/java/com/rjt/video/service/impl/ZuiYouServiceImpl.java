package com.rjt.video.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.rjt.common.util.JsonUtil;
import com.rjt.video.model.VideoModel;
import com.rjt.video.service.VideoService;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**  
* @comment
* @author tanran 
* @date 2019年6月14日  
* @version 1.0  
*/
@Service
public class ZuiYouServiceImpl implements VideoService {

	@Override
	public VideoModel parseUrl(String url) {
		// TODO Auto-generated method stub
		VideoModel videoModel=new VideoModel();
		/*HttpRequest request = HttpRequest.get(url);
		String res = request.body();
		System.out.println(res);*/
		try {
			Matcher matcher = Pattern.compile("\\d{9}").matcher(url);
			if(matcher.find()) {
				String pid=matcher.group(0);
				url="https://share.izuiyou.com/api/post/detail";
				OkHttpClient okHttpClient = new OkHttpClient();
				RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"pid\":"+pid+"}");
				Request request = new Request.Builder().url(url).post(body).build();
				Response response = okHttpClient.newCall(request).execute();
				String result=response.body().string();
				System.out.println(result);
				String id=JsonUtil.getJsonValue(result, "data.post.imgs[0].id");
				 videoModel.setName(JsonUtil.getJsonValue(result, "data.post.content"));
				 videoModel.setCover(JsonUtil.getJsonValue(result, "data.post.videos."+id+".cover_urls[0]"));
				 videoModel.setPlayAddr(JsonUtil.getJsonValue(result, "data.post.videos."+id+".url"));
			}	 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return videoModel;
	}
	
	public static void main(String[] args) {
		System.out.println(new ZuiYouServiceImpl().parseUrl("https://h5.izuiyou.com/detail/122595613?zy_to=applink&to=applink"));
	}
}
