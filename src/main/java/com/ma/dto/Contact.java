package com.ma.dto;


import java.io.Serializable;

public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer contactId;
	private String phone;
	private String name;

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
