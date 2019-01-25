package com.teplot.testapp.been;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ResultObj<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18129955594L;

	@Expose
	private int ret = -1;
	@Expose
	private E data;
	@Expose
	private String msg;

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
