/*
 * Copyright (c) 2022. Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.kaiserpfalzedv.commons.vaadin.mainlayout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ClickableRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.UIScoped;
import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.OidcSession;
import io.quarkus.oidc.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@Slf4j
public class MainView extends AppLayout implements HasDynamicTitle, BeforeEnterObserver {

    @ConfigProperty(name = "application.name", defaultValue = "Application")
    String appName;
    @ConfigProperty(name = "application.version", defaultValue = "999")
    String appVersion;

    HorizontalLayout header;
    VerticalLayout drawer;

    @Inject
    @IdToken
    JsonWebToken idToken;

    public MainView() {
        createDrawer();
    }

    private void createHeader() {
        log.debug("Creating header. appName='{}', version={}, subject='{}', name='{}'",
                appName, appVersion, idToken.getSubject(), idToken.getName());

        H1 logo = new H1(appName + " | v" + appVersion);

        Label user;
        if (idToken != null) {
            user = new Label(idToken.getName());
            log.info("subject={}, name={}, claims={}", idToken.getSubject(), idToken.getName(), idToken.getClaimNames());
        } else {
            log.warn("No user given!");
            user = new Label("not logged in!");
        }

        logo.addClassNames("text-1", "m-m");

        header = new HorizontalLayout(
                new DrawerToggle(),
                logo,
                user
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        log.debug("Creating drawer. session='{}'", UI.getCurrent().getSession().getSession().getId());
        drawer = new VerticalLayout();

        Button logout = new Button("Logout", e -> UI.getCurrent().getPage().setLocation("/logout"));

        drawer.add(logout);

        addToDrawer(drawer);
    }

    @Override
    public String getPageTitle() {
        log.trace("Generating page title. appName='{}', appVersion={}", appName, appVersion);
        return appName + " (v." + appVersion + ")";
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        log.trace("Entering view. view='{}'", this.getClassName());

        createHeader();
    }
}
