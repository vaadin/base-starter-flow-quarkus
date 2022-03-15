package com.example.starter.base;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.quarkus.QuarkusVaadinServlet;

@WebServlet(urlPatterns = "/*", name = "VaadinServlet", asyncSupported = true, initParams = {
        // TODO: Enable dev mode gizmo when issues with Atmosphere and Quarkus are resolved
        // https://github.com/vaadin/quarkus/issues/5
        @WebInitParam(name = "devmode.gizmo.enabled", value = "false")})
public class Servlet extends QuarkusVaadinServlet {
}
