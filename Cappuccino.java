package com.shop;


public class Cappuccino extends Coffee {
	private String milkType;

	public Cappuccino(String name, double price, String description, String milkType) {
		super(name, price, description);
		this.milkType = milkType;
	}

	public String getMilkType() {
		return milkType;
	}

	public void setMilkType(String milkType) {
		this.milkType = milkType;
	}

	@Override
	public void prepare() {
		super.prepare();
		System.out.println("Adding " + milkType + " milk.");
	}

}
