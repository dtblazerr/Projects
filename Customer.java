package com.shop;


public abstract class Customer {
	private String name;
	private String emailAddress;
	private String phone;

	public Customer(String name, String emailAddress, String phone) {
		this.name = name;
		this.emailAddress = emailAddress;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public abstract String getCustomerType();

	
}
