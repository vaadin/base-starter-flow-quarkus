package com.example.starter.base;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "order", layout = MainView.class)
public class OrderView extends VerticalLayout implements AfterNavigationObserver {

    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final TextField addressField = new TextField();
    @Inject
    private OrderModel orderModel;

    @PostConstruct
    public void init() {
        FormLayout formLayout = new FormLayout();

        firstNameField.setLabel("First name");
        firstNameField.setPlaceholder("John");
        firstNameField.addValueChangeListener(firstName ->
                orderModel.getFilledOrder().setFirstName(firstName.getValue()));

        lastNameField.setLabel("Last name");
        lastNameField.setPlaceholder("Doe");
        lastNameField.addValueChangeListener(lastName ->
                orderModel.getFilledOrder().setLastName(lastName.getValue()));

        addressField.setLabel("Address");
        addressField.setPlaceholder("Enter your delivery address");
        addressField.addValueChangeListener(address ->
                orderModel.getFilledOrder().setAddress(address.getValue()));

        formLayout.add(addressField, firstNameField, lastNameField);

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("1px", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("700px", 3));
        add(new H3("Step 3: Fill in the order details"));
        add(formLayout);

        add(new RouterLink("Select location", LocationSelectView.class),
                new RouterLink("Select product", ProductSelectView.class));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        String firstName = orderModel.getFilledOrder().getFirstName();
        if (firstName != null) {
            firstNameField.setValue(firstName);
        }

        String lastName = orderModel.getFilledOrder().getLastName();
        if (lastName != null) {
            lastNameField.setValue(lastName);
        }

        String address = orderModel.getFilledOrder().getAddress();
        if (address != null) {
            addressField.setValue(address);
        }
    }
}
