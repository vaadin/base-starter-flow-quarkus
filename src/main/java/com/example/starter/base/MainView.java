package com.example.starter.base;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;

@RoutePrefix("product")
public class MainView extends VerticalLayout implements RouterLayout {

    @Inject
    private LocationModel locationModel;

    @PostConstruct
    private void init() {
        add(new H2("Product selection wizard"));
    }
}
