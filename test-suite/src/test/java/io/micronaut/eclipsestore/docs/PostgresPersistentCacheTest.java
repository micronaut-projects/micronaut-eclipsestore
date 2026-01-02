package io.micronaut.eclipsestore.docs;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.util.StringUtils;
import io.micronaut.eclipsestore.testutils.Postgresql;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.testcontainers.DockerClientFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgresPersistentCacheTest {
    public static boolean dockerAvailable() {
        return DockerClientFactory.instance().isDockerAvailable();
    }

    @EnabledIf("dockerAvailable")
    @Test
    void cachePersistsOverRestarts() {
        Map<String, Object> config = new HashMap<>(Map.of(
            "datasources.cache.db-type", "postgresql",
            "micronaut.metrics.enabled", StringUtils.FALSE,
            "eclipsestore.postgres.storage.cache.table-name", "eclipsestore",
            "eclipsestore.cache.counter.key-type", "java.lang.String",
            "eclipsestore.cache.counter.value-type", "java.lang.Long",
            "eclipsestore.cache.counter.storage", "cache"
        ));
        Map<String, String> postgresProperties = Postgresql.getProperties("cache");
        for (String k : postgresProperties.keySet()) {
            config.put(k, postgresProperties.get(k));
        }

        // When we create the app, and use a cached method
        try (EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class, config)) {
            CounterService counter = server.getApplicationContext().getBean(CounterService.class);
            counter.setCount("Tim", 1337L);
            Long count = counter.currentCount("Tim");
            assertEquals(1337L, count);
            counter.setCount("Tim", 666L);
        }

        // Then restarting the app with the same storage location, the value is still cached
        try (EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class, config)) {
            CounterService counter = server.getApplicationContext().getBean(CounterService.class);
            Long count = counter.currentCount("Tim");
            assertEquals(666L, count);
        }
    }
}
