package com.example.starter.base;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.quarkus.annotation.RouteScopeOwner;
import com.vaadin.quarkus.annotation.RouteScoped;

@RouteScoped
@RouteScopeOwner(ProductSelectView.class)
@Route(value = "details", layout = ProductSelectView.class)
public class ProductDetailsView extends VerticalLayout {

    @Inject
    @RouteScopeOwner(ProductSelectView.class)
    private ProductModel model;

    private Registration registration;
    private Div detailsLabel;

    @PostConstruct
    private void init() {
        detailsLabel = new Div();
        add(new H2("Details"), detailsLabel);
        onProductUpdate();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        registration = model.addUpdateListener(this::onProductUpdate);
    }

    private void onProductUpdate() {
        final Product selectedProduct = model.getSelectedProduct();
        if (selectedProduct != null) {
            detailsLabel.setText(selectedProduct.getName() + " - " +
                                 selectedProduct.getDetails());
        } else {
            detailsLabel.setText("Please select a product first");
        }
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        registration.remove();
    }
}
