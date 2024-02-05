package com.example.starter.security;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.Route;

@Tag("div")
@Route(value = "login", registerAtStartup = false)
public class LoginView extends Component implements HasComponents {
}