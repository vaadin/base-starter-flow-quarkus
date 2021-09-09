package com.example.starter.base;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.quarkus.annotation.RouteScoped;
import com.vaadin.quarkus.annotation.UIScoped;

@UIScoped
@Route("")
@RoutePrefix("product")
public class MainView extends VerticalLayout implements RouterLayout {

    @PostConstruct
    private void init() {
        add(new H2("Product selection wizard"));
    }
}
