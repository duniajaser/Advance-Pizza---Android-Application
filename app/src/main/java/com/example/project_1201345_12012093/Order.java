package com.example.project_1201345_12012093;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private String customerEmail;
    private ArrayList<PizzaType> pizzas;
    private int quantity;
    private String orderDateTime;
    private double totalPrice;

    public Order() {
    }

    public Order(String customerEmail, ArrayList<PizzaType> pizzas, int quantity, String orderDateTime, double totalPrice) {
        this.customerEmail = customerEmail;
        this.pizzas = pizzas;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
    }

    public Order(String customerEmai, int quantity, String orderDateTime, double totalPrice) {
        this.customerEmail = customerEmail;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
    }

    public Order(int orderId, String customerEmail, ArrayList<PizzaType> pizzas, int quantity, String orderDateTime, double totalPrice) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.pizzas = pizzas;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public ArrayList<PizzaType> getPizzas() {
        return pizzas;
    }

    public void setPizzas(ArrayList<PizzaType> pizzas) {
        this.pizzas = pizzas;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerEmail='" + customerEmail + '\'' +
                ", pizzas=" + pizzas +
                ", quantity=" + quantity +
                ", orderDateTime='" + orderDateTime + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
