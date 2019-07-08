package com.rjt.video.service.impl;

import com.rjt.video.service.VideoService;

public class VideoFactory {
	public static VideoService getVideo(String type)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		if (type.indexOf("douyin.com") != -1 || type.indexOf("iesdouyin.com") != -1 ) {

			return DouyinServiceImpl.class.newInstance();

		} else if (type.indexOf("huoshan.com") != -1 )  {

			return HuoShanServiceImpl.class.newInstance();

		} else if (type.indexOf("kuaishou.com") != -1 || type.indexOf("gifshow.com") != -1 || type.indexOf("chenzhongtech.com") != -1) {

			return KuaiShouServiceImpl.class.newInstance();

		} else if (type.indexOf("pipix.com") != -1) {

			return PiPiXServiceImpl.class.newInstance();

		} else if (type.indexOf("weishi.qq.com")!= -1 ) {

			return WeiShiServiceImpl.class.newInstance();

		}else if (type.indexOf("izuiyou.com") != -1) {

			return ZuiYouServiceImpl.class.newInstance();

		} else {
			System.out.println("哎呀！找不到相应的实例化类啦！");
			return null;
		}

	}
}
