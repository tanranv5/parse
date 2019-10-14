package com.rjt.video.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import com.rjt.common.util.JsonUtil;
import com.rjt.common.util.TextUtil;
import com.rjt.video.model.VideoModel;
import com.rjt.video.service.VideoService;

import blade.kit.http.HttpRequest;

/**  
* @comment
* @author tanran 
* @date 2019年6月14日  
* @version 1.0  
*/
@Service
public class DouyinServiceImpl implements VideoService{

	@Override
	public VideoModel parseUrl(String url) {
		// TODO Auto-generated method stub
		VideoModel videoModel=new VideoModel();
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			Request request = new Request.Builder().url(url).header("User-Agent","Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1").build();
			Response response = okHttpClient.newCall(request).execute();
			String result=response.body().string();
			String vid=TextUtil.getSubString(result,"itemId:",",").trim();
			String dytk=TextUtil.getSubString(result,"dytk:","}").trim();
			vid=vid.substring(1,vid.length()-1);
			dytk=dytk.substring(1,dytk.length()-1);
			url="https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids="+vid+"&dytk="+dytk;
			request = new Request.Builder().url(url).header("User-Agent","Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1").build();
			response = okHttpClient.newCall(request).execute();
			result=response.body().string();
			System.out.println(result);
			String title = JsonUtil.getJsonValue(result, "item_list[0].desc");
			String playAddr = JsonUtil.getJsonValue(result, "item_list[0].video.play_addr_lowbr.url_list[0]");
			String cover = JsonUtil.getJsonValue(result, "item_list[0].video.origin_cover.url_list[0]");
			request = new Request.Builder().url(playAddr).header("User-Agent","Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1").build();
			response = okHttpClient.newCall(request).execute();
			result=response.body().string();
			result="<a href=\"http://v6-dy.ixigua.com/8eb705f7b551a10582a5f3e24fe0cfb1/5da43464/video/m/220eee6c0265487492e947931b21c5400b5116234a6d0000aecc8c3b8ff8/?a=1128&amp;br=1227&amp;cr=0&amp;cs=0&amp;dr=0&amp;ds=6&amp;er=&amp;l=20191014153939010155053153642053&amp;lr=&amp;rc=anhpdHRvOmd1bTMzO2kzM0ApaTxpOGU3PDs0NzQ1NTg4M2dsXmFeLmRxb2lfLS01LS9zcy42XjEzLWBgMy8vM2JjXmI6Yw%3D%3D\">Found</a>";
			playAddr=TextUtil.getSubString(result,"\"","\"");
			videoModel.setName(title);
			videoModel.setPlayAddr(playAddr);
			videoModel.setCover(cover);

		}catch (Exception e){
			e.printStackTrace();
		}
		return videoModel;
	}
	public static void main(String[] args) {
		System.out.println(new DouyinServiceImpl().parseUrl("http://v.douyin.com/r2w3sN/"));
	}
}
