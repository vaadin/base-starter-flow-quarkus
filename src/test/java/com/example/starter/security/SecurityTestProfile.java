package com.example.starter.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.auth.NavigationAccessControl;
import com.vaadin.testbench.unit.quarkus.mocks.MockQuarkusServletService;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.test.junit.QuarkusTestProfile;

public class SecurityTestProfile implements QuarkusTestProfile {

    @Override
    public String getConfigProfile() {
        return "test,security-test";
    }

    @ApplicationScoped
    @IfBuildProfile("security-test")
    public static class XX implements VaadinServiceInitListener {

        @Override
        public void serviceInit(@Observes ServiceInitEvent event) {
            // Currently, @QuarkusTest starts the whole application, so we check
            // the VaadinService type to register routes only for UI Unit tests
            if (event.getSource() instanceof MockQuarkusServletService) {
                RouteConfiguration routeConfiguration = RouteConfiguration
                        .forApplicationScope();
                routeConfiguration.setAnnotatedRoute(LoginView.class);
                routeConfiguration.setAnnotatedRoute(ProtectedView.class);
                event.getSource().addUIInitListener(uiEvent -> {
                    NavigationAccessControl accessControl = new NavigationAccessControl();
                    accessControl.setLoginView(LoginView.class);
                    uiEvent.getUI().addBeforeEnterListener(accessControl);
                });
            }
        }
    }
}
