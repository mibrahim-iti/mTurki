package com.ma.dto;

public class Message {

	private Integer status;
	private String data;

	public Message(Integer status, String data) {
		this.status = status;
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
