package com.teplot.testapp.gridview;


import android.graphics.Bitmap;

public class GridViewItem {

	private int imgId;
	private String id;
	private int cancelId;
	private Bitmap bitmap;
	private String type;//视频还是图片

	private String filePath;//相关文件的路径
	private String imgUri;//图片的uri

	private String title;//图片的uri

	//天气相关
	private String week;
	private String dateTime;
	private String weatherType;
	private String weatherTemp;
	private String weather;
	private String weatherWind;

	public GridViewItem(int imgId, int cancelId, String type, String filePath, String id) {
		this.imgId = imgId;
		this.cancelId = cancelId;
		this.type = type;
		this.filePath = filePath;
		this.id = id;
	}

	public GridViewItem(Bitmap bitmap, int cancelId, String type, String filePath, String id) {
		this.cancelId = cancelId;
		this.bitmap = bitmap;
		this.type = type;
		this.filePath = filePath;
		this.id = id;
	}
	public GridViewItem(String imgUri, int cancelId, String type, String filePath, String id) {
		this.cancelId = cancelId;
		this.imgUri = imgUri;
		this.type = type;
		this.filePath = filePath;
		this.id = id;
	}

	public GridViewItem(String week, String dateTime, String weatherType, String weatherTemp, String weather, String weatherWind) {
		this.week = week;
		this.dateTime = dateTime;
		this.weatherType = weatherType;
		this.weatherTemp = weatherTemp;
		this.weather = weather;
		this.weatherWind = weatherWind;
	}

	public GridViewItem(String imgUri, String title) {
		this.imgUri = imgUri;
		this.title = title;
	}

	public GridViewItem(String id, String imgUri, String title) {
		this.id = id;
		this.imgUri = imgUri;
		this.title = title;
	}

	public GridViewItem(int imgId, String title) {
		this.imgId = imgId;
		this.title = title;
	}

	public GridViewItem(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getWeatherType() {
		return weatherType;
	}

	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}

	public String getWeatherTemp() {
		return weatherTemp;
	}

	public void setWeatherTemp(String weatherTemp) {
		this.weatherTemp = weatherTemp;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWeatherWind() {
		return weatherWind;
	}

	public void setWeatherWind(String weatherWind) {
		this.weatherWind = weatherWind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public int getCancelId() {
		return cancelId;
	}

	public void setCancelId(int cancelId) {
		this.cancelId = cancelId;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getImgUri() {
		return imgUri;
	}

	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}
}
