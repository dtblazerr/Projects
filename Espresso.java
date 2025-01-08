package com.shop;


public class Espresso extends Coffee {
	private int shots;

	public Espresso(String name, double price, String description, int shots) {
		super(name, price, description);
		this.shots = shots;
	}

	public int getShots() {
		return shots;
	}

	public void setShots(int shots) {
		this.shots = shots;
	}

	@Override
	public void prepare() {
		super.prepare();
		System.out.println("Adding " + shots + " espresso shots.");
	}

}
