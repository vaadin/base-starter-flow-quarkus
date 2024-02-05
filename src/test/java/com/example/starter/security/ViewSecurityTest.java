package com.example.starter.security;

import com.vaadin.testbench.unit.ViewPackages;
import com.vaadin.testbench.unit.quarkus.QuarkusUIUnitTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@ViewPackages(packages = "com.example.starter.security")
@TestProfile(SecurityTestProfile.class)
class ViewSecurityTest extends QuarkusUIUnitTest {

    @Test
    @TestSecurity(user = "john", roles = {"DEV", "PO"})
    void withMockUser_landOnProtectedHomeView() {
        Assertions.assertInstanceOf(ProtectedView.class, getCurrentView(),
                "Logged user should land to protected home view");
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void withAnonymousUser_redirectToLogin() {
        Assertions.assertInstanceOf(LoginView.class, getCurrentView(),
                "Anonymous user should be redirect to login view");
    }

}
