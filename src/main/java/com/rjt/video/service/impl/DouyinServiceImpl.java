package com.rjt.video.service.impl;

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
		HttpRequest request = HttpRequest.get(url);
		String res = request.body();
		String awemeId=TextUtil.getSubString(res, "https://www.iesdouyin.com/share/video/", "/?");
		
		url ="https://api-hl.amemv.com/aweme/v1/aweme/detail/?aid=1128&app_name=aweme&version_code=251&aweme_id="+awemeId;
		request=HttpRequest.get(url).header("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
		res = request.body();
		System.out.println(res);
	    String title = JsonUtil.getJsonValue(res, "aweme_detail.share_info.share_title");
	    String playAddr = JsonUtil.getJsonValue(res, "aweme_detail.video.play_addr.url_list[0]");
	    String cover = JsonUtil.getJsonValue(res, "aweme_detail.video.origin_cover.url_list[0]");
	    videoModel.setName(title);
	    videoModel.setPlayAddr(playAddr);
	    videoModel.setCover(cover);
		return videoModel;
	}
	public static void main(String[] args) {
		System.out.println(new DouyinServiceImpl().parseUrl("http://v.douyin.com/r2w3sN/"));
	}
}
