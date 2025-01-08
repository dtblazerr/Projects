package com.shop;


public class PremiumCustomer extends Customer {
	private String memberID;

	public PremiumCustomer(String name, String address, String phone, String memberID) {
		super(name, address, phone);
		this.memberID = memberID;
	}

	public String getMemberID() {
		return memberID;
	}

	@Override
	public String getCustomerType() {
		return "Premium";
	}

	@Override
	public String toString() {
		return "PremiumCustomer [Member ID=" + memberID + ", Name:" + getName() + ", Email Address:" + getEmailAddress()
				+ ", Phone number=" + getPhone() + "]";
	}
	
	
}
