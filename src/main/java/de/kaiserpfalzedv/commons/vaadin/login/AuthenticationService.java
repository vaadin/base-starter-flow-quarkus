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

package de.kaiserpfalzedv.commons.vaadin.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.*;
import io.quarkus.oidc.IdTokenCredential;
import io.quarkus.oidc.SecurityEvent;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.security.Principal;
import java.util.Set;

/**
 * AuthenticationService --
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2.0.0  2022-05-27
 */
@ApplicationScoped
@Slf4j
public class AuthenticationService implements SecurityIdentityAugmentor, SessionInitListener, SessionDestroyListener {
    @Override
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
        if (DefaultJWTCallerPrincipal.class.isAssignableFrom(identity.getPrincipal().getClass())) {
            DefaultJWTCallerPrincipal principal = ((DefaultJWTCallerPrincipal) identity.getPrincipal());

            log.info("Augmenting identity. subject='{}', name='{}', issuer='{}'", principal.getSubject(), principal.getName(), principal.getIssuer());

            if (log.isDebugEnabled() && !principal.getGroups().isEmpty()) {
                log.debug("Groups for identity. subject={}, groups={}", principal.getSubject(), principal.getGroups());
            }
        }

        RoutingContext ctx = identity.getAttribute(RoutingContext.class.getName());

        if (
                ctx != null
                && ctx.normalizedPath().endsWith("/github")
        ) {
            QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder(identity);
            UserInfo userInfo = identity.getAttribute("userinfo");
            builder.setPrincipal(() -> userInfo.getString("preferred_username"));

            identity = builder.build();
        }

        return Uni.createFrom().item(identity);
    }

    @Override
    public void sessionInit(SessionInitEvent sessionInitEvent) throws ServiceException {
        log.info("Initializing session. session={}", sessionInitEvent.getSession().getSession().getId());
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent sessionDestroyEvent) {
        log.info("Destroying session. session={}", sessionDestroyEvent.getSession().getSession().getId());
        UI.getCurrent().navigate("logout");
    }

    public void event(@Observes SecurityEvent event) {
        String tenantId = event.getSecurityIdentity().getAttribute("tenant-id");
        DefaultJWTCallerPrincipal principal = (DefaultJWTCallerPrincipal) event.getSecurityIdentity().getPrincipal();

        log.debug("Security event. tenant={}, issuer='{}', principal='{}', event={}",
                tenantId, principal.getIssuer(),
                principal.getName(),
                event.getEventType().name());

    }
}
