package com.shop;


public class RegularCustomer extends Customer {
	private int loyaltyPoints;
	
	public RegularCustomer(String name, String address, String phone, int loyaltyPoints) {
	    super(name, address, phone);
	    this.loyaltyPoints = loyaltyPoints;
	}

	public int getLoyaltyPoints() {
	    return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
	    this.loyaltyPoints = loyaltyPoints;
	}

	@Override
	public String getCustomerType() {
	    return "Regular";
	}

	@Override
	public String toString() {
		return "RegularCustomer [loyaltyPoints=" + loyaltyPoints + ", Name:" + getName() + ", Email Address:="
				+ getEmailAddress() + ", Phone Number=" + getPhone() + "]";
	}

	
}
