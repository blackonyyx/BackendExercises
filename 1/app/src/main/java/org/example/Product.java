package org.example;

public class Product {
    double price;
    String name;
    String category;
    public Product(double price, String name, String category) {
        this.price = price;
        this.name = name;
        this.category = category;
    }
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
    @Override
    public String toString() {
        return price + "," + category +"," + name;
    }
}
