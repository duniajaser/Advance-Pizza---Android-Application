package com.example.project_1201345_12012093;

import java.util.ArrayList;

public class SpecialOffer {
    private ArrayList<PizzaType> pizzas;
    private String startingOfferDate;
    private String endingOfferDate;
    private double totalPrice;
    private int quantity;

    public SpecialOffer() {
        pizzas = new ArrayList<>();
    }

    public ArrayList<PizzaType> getPizzas() {
        return pizzas;
    }

    public void setPizzas(ArrayList<PizzaType> pizzas) {
        this.pizzas = pizzas;
    }

    public void addPizza(PizzaType pizza) {
        this.pizzas.add(pizza);
    }

    public String getStartingOfferDate() {
        return startingOfferDate;
    }

    public void setStartingOfferDate(String startingOfferDate) {
        this.startingOfferDate = startingOfferDate;
    }

    public String getEndingOfferDate() {
        return endingOfferDate;
    }

    public void setEndingOfferDate(String endingOfferDate) {
        this.endingOfferDate = endingOfferDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
