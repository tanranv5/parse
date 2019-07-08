package com.rjt.video.controller;

import java.util.Objects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjt.video.model.VideoModel;
import com.rjt.video.service.VideoService;
import com.rjt.video.service.impl.VideoFactory;

/**  
* @comment
* @author tanran 
* @date 2019年6月14日  
* @version 1.0  
*/
@RestController
@RequestMapping("video")
@CrossOrigin
public class VideoController {
	 @GetMapping("parse")
	   public VideoModel parse(String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		         VideoService videoService = VideoFactory.getVideo(url);
		         if(Objects.isNull(videoService)) {
		        	 return new VideoModel();
		         }
		         return videoService.parseUrl(url);
	   }
}
