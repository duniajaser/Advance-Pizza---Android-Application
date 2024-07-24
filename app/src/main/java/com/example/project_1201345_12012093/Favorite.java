package com.example.project_1201345_12012093;

public class Favorite {
    private String customerEmail;
    private String pizzaType;
    private String pizzaSize;


    // Constructor
    public Favorite(String customerEmail, String pizzaType) {
        this.customerEmail = customerEmail;
        this.pizzaType = pizzaType;

    }


    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }


    // Method to display favorite pizza details
    @Override
    public String toString() {
        return "Favorite{" +
                "customerEmail='" + customerEmail + '\'' +
                ", pizzaType='" + pizzaType + '\'' +
                ", pizzaSize='" + pizzaSize + '\'' +
                '}';
    }
}
