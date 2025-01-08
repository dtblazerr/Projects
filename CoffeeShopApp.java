package com.shop;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class CoffeeShopApp extends JFrame {
	private static final long serialVersionUID = 1L;
	private final String filename = "listOfOrders.txt";
	private List<Order> orders;
	private JPanel orderPanel, viewOrdersPanel;
	private JTabbedPane tabbedPane;
	private JTextField nameField, emailField, phoneField, loyaltyPointsField, memberIdField;
	private JComboBox<String> customerTypeBox;
	private JList<Coffee> coffeeList;
	private JButton placeOrderButton;
	private DefaultListModel<Coffee> coffeeListModel;
	private JTextArea viewOrdersArea;

	public CoffeeShopApp() {
		super("Coffee Shop App");
		orders = new ArrayList<>();

		// load orders from file
		orders = Order.loadOrders(filename);
		// set up GUI components
		setupGUI();


		// set window properties
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void setupGUI() {
	
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem orderCoffeeItem = new JMenuItem("Order Coffee");
		orderCoffeeItem.addActionListener(new OrderButtonListener());
		fileMenu.add(orderCoffeeItem);
		JMenuItem viewOrdersItem = new JMenuItem("View Orders");
		viewOrdersItem.addActionListener(new ViewOrdersButtonListener());
		fileMenu.add(viewOrdersItem);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ExitButtonListener());
		fileMenu.add(exitItem);
		menubar.add(fileMenu);
		setJMenuBar(menubar);;
	
		orderPanel = new JPanel(new BorderLayout());
		JPanel customerInfoPanel = new JPanel(new GridLayout(6, 2));
		customerInfoPanel.add(new JLabel("Name:"));
		nameField = new JTextField();
		customerInfoPanel.add(nameField);
		customerInfoPanel.add(new JLabel("Email:"));
		emailField = new JTextField();
		customerInfoPanel.add(emailField);
		customerInfoPanel.add(new JLabel("Phone:"));
		phoneField = new JTextField();
		customerInfoPanel.add(phoneField);
		customerInfoPanel.add(new JLabel("Customer Type:"));
		String[] customerTypes = { "Regular", "Premium" };
		customerTypeBox = new JComboBox<>(customerTypes);
		customerInfoPanel.add(customerTypeBox);
		JLabel memberLabel = new JLabel("Member ID:");
		customerInfoPanel.add(memberLabel);
	    memberIdField = new JTextField();
	    customerInfoPanel.add(memberIdField);
	    memberLabel.setVisible(false);
	    memberIdField.setVisible(false);

	    JLabel loyaltyPointsLabel = new JLabel("Loyalty Points:");
	    customerInfoPanel.add(loyaltyPointsLabel);
	    loyaltyPointsField = new JTextField();
	    customerInfoPanel.add(loyaltyPointsField);
	    loyaltyPointsLabel.setVisible(true);
	    loyaltyPointsField.setVisible(true);

		orderPanel.add(customerInfoPanel, BorderLayout.NORTH);
	    customerTypeBox.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String customerType = (String) customerTypeBox.getSelectedItem();
	            if (customerType.equals("Premium")) {
	            	memberLabel.setVisible(true);
	            	memberIdField.setVisible(true);
	            	loyaltyPointsLabel.setVisible(false);
	            	loyaltyPointsField.setVisible(false);
	            } else
	            {
	            	memberLabel.setVisible(false);
	            	memberIdField.setVisible(false);
	            	loyaltyPointsLabel.setVisible(true);
	            	loyaltyPointsField.setVisible(true);
	            }
	        }
	    });
	   	    
		JPanel coffeePanel = new JPanel(new BorderLayout());
		coffeeListModel = new DefaultListModel<>();
		coffeeListModel.addElement(Order.findCoffee("Espresso"));
		coffeeListModel.addElement(Order.findCoffee("Cappuccino"));
		coffeeListModel.addElement(Order.findCoffee("Latte"));
		coffeeListModel.addElement(Order.findCoffee("Mocha"));

		coffeeList = new JList<>(coffeeListModel);
		JScrollPane coffeeListScrollPane = new JScrollPane(coffeeList);
		coffeePanel.add(coffeeListScrollPane, BorderLayout.CENTER);
		orderPanel.add(coffeePanel, BorderLayout.CENTER);
		placeOrderButton = new JButton("Place Order");
		placeOrderButton.addActionListener(new PlaceOrderButtonListener());
		JPanel placeOrderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		placeOrderPanel.add(placeOrderButton);
		orderPanel.add(placeOrderPanel, BorderLayout.SOUTH);

		viewOrdersPanel = new JPanel(new BorderLayout());
		viewOrdersArea = new JTextArea(10, 50);
		// Display the list of orders
		viewOrdersArea.setText("");
		for (Order order : orders) {
			viewOrdersArea.append(order.toString() + "\n");
		}
		JScrollPane viewOrdersScrollPane = new JScrollPane(viewOrdersArea);
		viewOrdersPanel.add(viewOrdersScrollPane, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Order Coffee", orderPanel);
		tabbedPane.addTab("View Orders", viewOrdersPanel);
		getContentPane().add(tabbedPane);
	}

	private class OrderButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tabbedPane.setSelectedComponent(orderPanel);
		}
	}

	private class ViewOrdersButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tabbedPane.setSelectedComponent(viewOrdersPanel);
		}
	}
	
	private class ExitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new CoffeeShopApp().setVisible(true);
	}

	private class PlaceOrderButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Get the customer information
			String name = nameField.getText();
			String emailAddress = emailField.getText();
			String phone = phoneField.getText();
			// Validate customer information
			if (name.isEmpty() || emailAddress.isEmpty() || phone.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter all customer information to place order.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Get coffee item
			Coffee coffee = coffeeList.getSelectedValue();
			if(coffee ==null){
				JOptionPane.showMessageDialog(null, "Please select a coffee to place order.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
				
			}
			// Get customer type and loyalty points or member ID
			String customerType = (String) customerTypeBox.getSelectedItem();
			int loyaltyPoints = 0;
			String memberID = null;
			if (("Regular").equals(customerType)) {
				try {
					loyaltyPoints = Integer.parseInt(loyaltyPointsField.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter a valid integer for loyalty points to place order.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else {
				memberID = memberIdField.getText();
				if (memberID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter the member ID to place order.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			// Validate order
			if ("Premium".equals(customerType) && memberID == null) {
				JOptionPane.showMessageDialog(null, "Please enter the member ID for premium customers.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}			

			Customer customer = null;
			if ("Premium".equals(customerType)) {
				customer = new PremiumCustomer(name, emailAddress, phone, memberID);
			} else {
				customer = new RegularCustomer(name, emailAddress, phone, loyaltyPoints);
			}
			// Create a new Order object
			Order order = new Order(customer, filename);
			order.addCoffee(coffee);
			// Save the orders to the txt file
			order.placeOrder();
			// Add the order to the list of orders
			orders.add(order);
			
			viewOrdersArea.setText("");

			// Display the list of orders
			for (Order viewOrder : orders) {
				viewOrdersArea.append(viewOrder.toString() + "\n");
			}
			nameField.setText("");
			emailField.setText("");
			phoneField.setText("");
			loyaltyPointsField.setText("");
			// Display View Orders tab
			tabbedPane.setSelectedIndex(1);
		}
	}
}
