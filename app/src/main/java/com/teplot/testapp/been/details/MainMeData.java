package com.teplot.testapp.been.details;

/**
 * 侧滑栏显示
 * @author Administrator
 *
 */
public class MainMeData {

	public String title;
	public int imgId;
	public int imgId2;
	public String content;
	public boolean isOpen;


	public MainMeData(String title, int imgId, int imgId2, String content) {
		this.title = title;
		this.imgId = imgId;
		this.imgId2 = imgId2;
		this.content = content;
	}

	public MainMeData(String title, String content, boolean isOpen) {
		this.title = title;
		this.content = content;
		this.isOpen = isOpen;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean open) {
		isOpen = open;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getImgId2() {
		return imgId2;
	}

	public void setImgId2(int imgId2) {
		this.imgId2 = imgId2;
	}
}
