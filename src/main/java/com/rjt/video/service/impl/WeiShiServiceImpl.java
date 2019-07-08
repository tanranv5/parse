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
public class WeiShiServiceImpl implements VideoService {

	@Override
	public VideoModel parseUrl(String url) {
		// TODO Auto-generated method stub
		VideoModel videoModel=new VideoModel();
		try {
			Matcher matcher = Pattern.compile("\\w{17}").matcher(url);
			if(matcher.find()) {
				String feedId=matcher.group(0);
				url="https://h5.qzone.qq.com/webapp/json/weishi/WSH5GetPlayPage?feedid="+feedId;
				OkHttpClient okHttpClient = new OkHttpClient();
				Request request = new Request.Builder().url(url).build();
				Response response = okHttpClient.newCall(request).execute();
				String result=response.body().string();
				System.out.println(result);
				 videoModel.setName(JsonUtil.getJsonValue(result, "data.feeds[0].material_desc"));
				 videoModel.setPlayAddr(JsonUtil.getJsonValue(result, "data.feeds[0].video_url"));
				 videoModel.setCover(JsonUtil.getJsonValue(result, "data.feeds[0].images[0].url"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return videoModel;
	}
	public static void main(String[] args) {
		System.out.println(new WeiShiServiceImpl().parseUrl("https://h5.weishi.qq.com/weishi/feed/70hxLzG4U1HlJhtUj/wsfeed?_proxy=1&_wv=1&wxplay=1&id=70hxLzG4U1HlJhtUj&spid=h5&reqseq=311149196&cover=http%3A%2F%2Fpic640.weishi.qq.com%2Fab4595845793449cb17a6df26bb6cover.jpg&bgSize=cover&image=4595845793449cb17a6df26bb6cover.&chid=100000001&pkg=3670&attach=cp_reserves3_4001&qua=v1_ht5_qz_3.0.0_001_idc_new&xflag=2130706433a1685170850b1560498191"));
	}

}
