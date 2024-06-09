package org.dexterity.darueira.azimuteerp.monolith.springvue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.dexterity.darueira.azimuteerp.monolith.springvue.config.AsyncSyncConfiguration;
import org.dexterity.darueira.azimuteerp.monolith.springvue.config.EmbeddedSQL;
import org.dexterity.darueira.azimuteerp.monolith.springvue.config.JacksonConfiguration;
import org.dexterity.darueira.azimuteerp.monolith.springvue.config.TestSecurityConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        AzimuteErpSpringVueMonolith04App.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class,
    }
)
@EmbeddedSQL
public @interface IntegrationTest {
}
