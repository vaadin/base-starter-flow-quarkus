package com.example.starter.base;

import com.vaadin.testbench.unit.UIUnitTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


@QuarkusTest
class MainViewTest extends UIUnitTest {

    @Test
    void accessView() {
        MainView mainView = navigate(MainView.class);
        Assertions.assertNotNull(mainView);
    }
}