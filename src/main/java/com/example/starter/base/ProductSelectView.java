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
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.quarkus.annotation.NormalUIScoped;
import com.vaadin.quarkus.annotation.RouteScopeOwner;
import com.vaadin.quarkus.annotation.RouteScoped;
import com.vaadin.quarkus.annotation.UIScoped;

@RouteScoped
@Route(value = "select")
public class ProductSelectView extends VerticalLayout implements RouterLayout
        , AfterNavigationObserver {

    @Inject
    @RouteScopeOwner(ProductSelectView.class)
    private ProductModel productModel;

    @Inject
    private LocationModel locationModel;

    @Inject
    private ProductService productService;

    private Select<Product> select;

    @PostConstruct
    private void init() {
        select = new Select<>();
        select.setLabel("Select a product");
        select.setItemLabelGenerator(Product::getName);
        select.addValueChangeListener(event -> productModel.setSelectedProduct(event.getValue()));
        Location selectedLocation = locationModel.getSelectedLocation();
        select.setItems(productService.getProductsByLocation(selectedLocation));
        add(new H3("Step 2: Select a product to order"));
        add(select);
        add(new Span("Location: " + (selectedLocation != null ?
                selectedLocation.getCity() : "All")));

        add(new RouterLink("Show Product Details", ProductDetailsView.class),
                new RouterLink("Show Product Stocks", ProductStockView.class),
                new RouterLink("Select product from scratch", LocationSelectView.class));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        if (productModel.getSelectedProduct() != null) {
            select.setValue(productModel.getSelectedProduct());
        }
    }
}
