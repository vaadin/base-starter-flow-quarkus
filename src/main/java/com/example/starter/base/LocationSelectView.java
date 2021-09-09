package com.example.starter.base;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@RoutePrefix("")
@Route(value = "location", layout = MainView.class)
public class LocationSelectView extends VerticalLayout implements RouterLayout {

    @Inject
    private LocationModel locationModel;

    @Inject
    private LocationService locationService;

    @PostConstruct
    private void init() {
        Select<Location> locationSelect = new Select<>();
        locationSelect.setItems(locationService.getLocations());
        locationSelect.setLabel("Select a location");
        locationSelect.addValueChangeListener(event ->
                locationModel.setSelectedLocation(event.getValue()));
        add(new H3("Step 1: Select a location to order product from"));
        add(locationSelect);
        add(new RouterLink("Select a product", ProductDetailsView.class));
    }
}
