package com.example.starter.base;

import java.util.Objects;

public class Order {
    private String firstName;
    private String lastName;
    private String address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(firstName, order.firstName) && Objects.equals(lastName, order.lastName) && Objects.equals(address, order.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address);
    }
}
