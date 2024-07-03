package ru.clevertec.check;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private boolean isWholesale;

    public Product(int id, String name, double price, int quantity, boolean isWholesale) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isWholesale = isWholesale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isWholesale() {
        return isWholesale;
    }

    public void setWholesale(boolean wholesale) {
        isWholesale = wholesale;
    }

    @Override
    public boolean equals(Object obj) {
        Product product = (Product) obj;
        return product.getId() == id
                && product.getName().equals(name)
                && product.getPrice() == price
                && product.getQuantity() == quantity
                && product.isWholesale() == isWholesale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity, isWholesale);
    }
}
