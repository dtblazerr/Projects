package com.shop;


public class Coffee {
	private String name;
	private double price;
	private String description;

	public Coffee(String name, double price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void prepare() {
		System.out.println("Preparing " + name + "...");
	}

	@Override
	public String toString() {
		return "Coffee [name=" + name + ", price=" + price + ", description=" + description + "]";
	}
	
	

}
