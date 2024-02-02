package com.example.starter.base;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.testbench.unit.quarkus.QuarkusUIUnitTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


@QuarkusTest
class MainViewTest extends QuarkusUIUnitTest {

    @Test
    void accessView() {
        MainView mainView = navigate(MainView.class);
        Assertions.assertNotNull(mainView);
    }

    @Test
    void useInjectedService() {
        MainView mainView = navigate(MainView.class);
        test(mainView.textField).setValue("User");
        test(mainView.button).click();
        Assertions.assertEquals("Hello User", test($(Paragraph.class, mainView).single()).getText());
    }

}
