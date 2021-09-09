package com.example.starter.base;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.quarkus.annotation.UIScoped;

@UIScoped
@Route(value = "location", layout = MainView.class)
public class LocationSelectView extends VerticalLayout implements AfterNavigationObserver {

    @Inject
    private LocationModel locationModel;

    @Inject
    private LocationService locationService;

    private Select<Location> locationSelect;

    @PostConstruct
    private void init() {
        locationSelect = new Select<>();
        locationSelect.setItems(locationService.getLocations());
        locationSelect.setLabel("Select a location");
        locationSelect.addValueChangeListener(event ->
                locationModel.setSelectedLocation(event.getValue()));
        add(new H3("Step 1: Select a location to order product from"));
        add(locationSelect);
        add(new RouterLink("Select a product", ProductSelectView.class),
                new RouterLink("Fill order", OrderView.class));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        Location selectedLocation = locationModel.getSelectedLocation();
        if (selectedLocation != null) {
            locationSelect.setValue(selectedLocation);
        }
    }
}
