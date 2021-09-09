package com.example.starter.base;

import com.vaadin.quarkus.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class OrderModel {
    private Order filledOrder = new Order();

    public Order getFilledOrder() {
        return filledOrder;
    }

    public void setFilledOrder(Order filledOrder) {
        this.filledOrder = filledOrder;
    }
}
