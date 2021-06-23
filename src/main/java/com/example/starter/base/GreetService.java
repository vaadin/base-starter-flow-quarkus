package com.example.starter.base;

import javax.enterprise.context.Dependent;

@Dependent
public class GreetService {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name;
        }
    }
}
