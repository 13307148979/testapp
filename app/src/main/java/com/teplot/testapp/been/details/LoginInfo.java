package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

/**
 * 登陆
 * @author Administrator
 *
 */
public class LoginInfo {

	@Expose
	public int id;
	@Expose
	public String account;
	@Expose
	public String password;
	@Expose
	public String userName;
	@Expose
	public String telephone;
	@Expose
	public String email;
	@Expose
	public String icon;
	@Expose
	public String status;
	@Expose
	public String sex;
	@Expose
	public String sign;
	@Expose
	public String weiXin;
	@Expose
	public String address;
	@Expose
	public String registerTime;
	@Expose
	public String realNameStatus;

	@Expose
	public String areaId;
	@Expose
	public String areaName;

	@Expose
	public String weather_info;//1是开  2是关
	@Expose
	public String new_message;
	@Expose
	public String disturb_mode;
	@Expose
	public String disturb_time_start;
	@Expose
	public String disturb_time_end;




	public LoginInfo() {
	}

	public LoginInfo(String account, String password) {
		this.account = account;
		this.password = password;
	}

	public LoginInfo(int id, String userName, String icon, String sex, String sign, String weiXin, String address, String areaId) {
		this.id = id;
		this.userName = userName;
		this.icon = icon;
		this.sex = sex;
		this.sign = sign;
		this.weiXin = weiXin;
		this.address = address;
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getWeather_info() {
		return weather_info;
	}

	public void setWeather_info(String weather_info) {
		this.weather_info = weather_info;
	}

	public String getNew_message() {
		return new_message;
	}

	public void setNew_message(String new_message) {
		this.new_message = new_message;
	}

	public String getDisturb_mode() {
		return disturb_mode;
	}

	public void setDisturb_mode(String disturb_mode) {
		this.disturb_mode = disturb_mode;
	}

	public String getDisturb_time_start() {
		return disturb_time_start;
	}

	public void setDisturb_time_start(String disturb_time_start) {
		this.disturb_time_start = disturb_time_start;
	}

	public String getDisturb_time_end() {
		return disturb_time_end;
	}

	public void setDisturb_time_end(String disturb_time_end) {
		this.disturb_time_end = disturb_time_end;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getWeiXin() {
		return weiXin;
	}

	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRealNameStatus() {
		return realNameStatus;
	}

	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
}
