package com.example.starter.base;

import java.util.Arrays;
import java.util.Collection;

import com.vaadin.quarkus.annotation.VaadinServiceScoped;

@VaadinServiceScoped
public class LocationService {
    public Collection<Location> getLocations() {
        return Arrays.asList(new Location("Turku"), new Location("Helsinki"));
    }
}
