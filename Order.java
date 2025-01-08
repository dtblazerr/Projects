package com.shop;


import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Order {
    private Coffee coffee;
    private Customer customer;
    private String filename;
    
    public Order(Customer customer, String filename) {
        this.customer = customer;
        this.filename = filename;
    }
    
    public void addCoffee(Coffee coffee) {
        this.coffee = coffee;
    }
        
    public void placeOrder() {
        saveOrder();
        printOrder();
    }
    
    private void saveOrder() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            if("Regular".equals(customer.getCustomerType())){
            	System.out.println("saving regular Customer " );
            	RegularCustomer cust = (RegularCustomer)customer;
            	writer.println(cust.getName() + "," + cust.getEmailAddress() + "," + cust.getPhone() + "," + cust.getCustomerType()+","+cust.getLoyaltyPoints()+"," +coffee.getName());
            }else{
            	PremiumCustomer cust = (PremiumCustomer)customer;
                writer.println(cust.getName() + "," + cust.getEmailAddress() + "," + cust.getPhone() + "," + cust.getCustomerType()+","+cust.getMemberID()+"," +coffee.getName());
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    private void printOrder() {
        System.out.println("Customer: " + customer.getName());
        System.out.println("Customer type: " + customer.getCustomerType());
        System.out.println("Email Address: " + customer.getEmailAddress());
        System.out.println("Phone: " + customer.getPhone());
       
         System.out.println("Ordered  "+ coffee.getName() + " (" + coffee.getDescription() + ") - $" + coffee.getPrice());
        System.out.println();
    }
    
    public static List<Order> loadOrders(String filename) {
    	List<Order> orders = new ArrayList<>();
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String name = parts[0];
				String address = parts[1];
				String phone = parts[2];
				String type = parts[3];
				Customer customer;
				if (type.equals("Regular")) {
					int loyaltyPoints = Integer.parseInt(parts[4]);
					customer = new RegularCustomer(name, address, phone, loyaltyPoints);
				} else {
					String memberID = parts[4];
					customer = new PremiumCustomer(name, address, phone, memberID);
				}
				String coffeeName = parts[5];
				Coffee coffee = findCoffee(coffeeName);
				Order order = new Order(customer, filename);
				order.addCoffee(coffee);
				orders.add(order);
			}
		} catch (IOException e) {
			System.err.println("Error loading orders from file: " + e.getMessage());
		}
		return orders;
    }

	public Customer getCustomer() {
		return customer;
	}
    
	public static Coffee findCoffee(String name) {
		if("espresso".equals(name.toLowerCase())){
			return new Espresso("Espresso", 2.50, "A single shot of espresso", 1);
		}
		else if("cappuccino".equals(name.toLowerCase())){
			return new Cappuccino("Cappuccino", 3.49, "Espresso with steamed milk and foam", "whole");
		}
		else if("cappuccino".equals(name.toLowerCase())){
			return new Coffee("Latte", 3.99, "Espresso with steamed milk");
		}
		else {
			return new Coffee("Mocha", 4.49, "Espresso with steamed milk and chocolate");
		}	
	}
	public Coffee getCoffee() {
		return coffee;
	}
	@Override
	public String toString() {
		return customer.toString() +"," + coffee.getName()+ "]";
	}
}
