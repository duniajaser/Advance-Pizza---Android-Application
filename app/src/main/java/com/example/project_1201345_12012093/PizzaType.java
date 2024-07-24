package com.example.project_1201345_12012093;

import java.util.ArrayList;

public class PizzaType {
    private String name;
    private float price;

    private String category;
    private int quantity;
    private String size;  // Size of the pizza
    private static ArrayList<PizzaType> pizzaTypes = new ArrayList<>();




    public PizzaType(String pizzaType, String size,float price, int quantity) {
        this.name = pizzaType;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public static ArrayList<PizzaType> getPizzaTypes() {
        return pizzaTypes;
    }

    public static void addPizzaType(String pizzaType) {
        PizzaType.pizzaTypes.add((new PizzaType(pizzaType)));
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PizzaType(String name) {
        this.name = name;
    }

    public PizzaType(String name, String size, int quantity) {
        this.name = name;
        this.size = size;
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
