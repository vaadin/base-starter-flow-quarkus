package com.example.starter.base;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

import com.vaadin.quarkus.annotation.VaadinServiceScoped;

@VaadinServiceScoped
public class ProductService {

    private static final Random random = new Random();
    private static final Collection<Product> products =
            Arrays.asList(
                    new Product("Hammer", "You can use it to hit things",
                            random.nextInt(10000), new Location("Turku")),
                    new Product("Swiss Army Knife", "The only thing you need",
                            random.nextInt(1000),
                            new Location("Turku")),
                    new Product("T-shirt", "Black. One size fits all",
                            random.nextInt(4000),
                            new Location("Helsinki")),
                    new Product("Mountain Bike", "Wheels 28', Frame 60 cm",
                            random.nextInt(4000),
                            new Location("Helsinki")));

    public Collection<Product> getProductsByLocation(Location location) {
        return products.stream()
                .filter(p -> location == null || p.getLocation().equals(location))
                .collect(Collectors.toList());
    }
}
