package com.example.starter.base;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.quarkus.annotation.RouteScopeOwner;
import com.vaadin.quarkus.annotation.RouteScoped;
import com.vaadin.quarkus.annotation.UIScoped;

@UIScoped
@Route(value = "select", layout = MainView.class)
public class ProductSelectView extends VerticalLayout implements AfterNavigationObserver {

    @Inject
    @RouteScopeOwner(MainView.class)
    private ProductModel productModel;

    @Inject
    private LocationModel locationModel;

    @Inject
    private ProductService productService;

    private final Select<Product> select = new Select<>();

    private final Span locationSpan = new Span("Unknown Location");

    @PostConstruct
    private void init() {
        select.setLabel("Select a product");
        select.setItemLabelGenerator(Product::getName);
        select.addValueChangeListener(event -> productModel.setSelectedProduct(event.getValue()));
        Location selectedLocation = locationModel.getSelectedLocation();
        select.setItems(productService.getProductsByLocation(selectedLocation));
        showLocation();
        add(new H3("Step 2: Select a product to order"));
        add(select);
        add(locationSpan);

        add(new RouterLink("Select location", LocationSelectView.class));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        Product selectedProduct = productModel.getSelectedProduct();
        if (selectedProduct != null) {
            select.setValue(selectedProduct);
        }
    }

    private void showLocation() {
        Location selectedLocation = locationModel.getSelectedLocation();
        locationSpan.setText("Location: " + (selectedLocation != null ?
                    selectedLocation.getCity() : "All"));
    }
}
