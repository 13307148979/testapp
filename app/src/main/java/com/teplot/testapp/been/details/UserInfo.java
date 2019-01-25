package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

/**
 * 登陆
 * @author Administrator
 *
 */
public class UserInfo {

	@Expose
	public String id;
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
	public String code;

	@Expose
	public String oldPassword;
	@Expose
	public String newPassword;

	public UserInfo() {
	}

	public UserInfo(String telephone) {
		this.telephone = telephone;
	}

	public UserInfo(String telephone, String code, String oldPassword, String newPassword, String account, String password) {
		this.telephone = telephone;
		this.code = code;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.account = account;
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
