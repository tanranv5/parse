package com.rjt.video.model;

public class VideoModel {
	//视频名
	private String name;
	//视频背景
	private String cover;
	//无水印地址
	private String playAddr;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}
	/**
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}
	/**
	 * @return the playAddr
	 */
	public String getPlayAddr() {
		return playAddr;
	}
	/**
	 * @param playAddr the playAddr to set
	 */
	public void setPlayAddr(String playAddr) {
		this.playAddr = playAddr;
	}
	@Override
	public String toString() {
		return "VideoModel [name=" + name + ", cover=" + cover + ", playAddr=" + playAddr + "]";
	}
	
	
}
