package com.example.starter.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vaadin.flow.server.Command;
import com.vaadin.flow.shared.Registration;
import com.vaadin.quarkus.annotation.NormalRouteScoped;
import com.vaadin.quarkus.annotation.RouteScopeOwner;

@NormalRouteScoped
@RouteScopeOwner(ProductSelectView.class)
public class ProductModel implements Serializable {

    private String uuid = UUID.randomUUID().toString();
    private List<Command> listeners = new ArrayList<>(2);
    private Product selectedProduct;

    public String getUuid() {
        return uuid;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        notifyListeners();
    }

    public Registration addUpdateListener(Command listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    private void notifyListeners() {
        listeners.forEach(Command::execute);
    }
}
