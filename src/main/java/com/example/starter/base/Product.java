package com.example.starter.base;

import java.util.Objects;

public class Product {
    private String name;
    private String details;
    private int quantity;
    private Location location;

    public Product(String name, String details, int quantity, Location location) {
        this.name = name;
        this.details = details;
        this.quantity = quantity;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity && Objects.equals(name, product.name) && Objects.equals(details, product.details) && Objects.equals(location, product.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, details, quantity, location);
    }
}
