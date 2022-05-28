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

package de.kaiserpfalzedv.commons.security;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.HashMap;

/**
 * User --
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2.0.0  2022-05-28
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "subject", query = "select Person FROM Person WHERE issuer = :issuer and subject = :subject ORDER BY name")
})
@Slf4j
public class Person extends PanacheEntity {
    public String issuer;
    public String subject;
    public String name;

    public static Uni<Person> findByIssuerAndSubject(final String issuer, final String subject) {
        return find("#subject", Parameters.with("issuer", issuer).and("subject", subject))
                .firstResult();
    }

    public static Long deleteByIssuerAndSubject(final String issuer, final String subject) {
        return delete("#subject", Parameters.with("issuer", issuer).and("subject", subject));
    }
}
