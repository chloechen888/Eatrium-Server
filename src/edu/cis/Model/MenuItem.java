package edu.cis.Model;

import java.util.ArrayList;

public class MenuItem {
    String name;
    String description;
    double price;
    String id;
    int amountAvailable;
    String type;

    public MenuItem(String name, String description, double price, String id, int amountAvailable, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
        this.amountAvailable = amountAvailable;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", id='" + id + '\'' +
                ", amountAvailable=" + amountAvailable +
                ", type='" + type + '\'' +
                '}';
    }
}
