package io.micronaut.eclipsestore.docs;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.util.StringUtils;
import io.micronaut.eclipsestore.testutils.DynamoDbLocal;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class DynamoDbPersistentCacheTest {

    public static boolean dockerAvailable() {
        return DockerClientFactory.instance().isDockerAvailable();
    }

    @Container
    public final DynamoDbLocal dynamoDbLocal = new DynamoDbLocal();

    @EnabledIf("dockerAvailable")
    @Test
    void cachePersistsOverRestarts() {
        var config = new HashMap<>(dynamoDbLocal.getProperties());
        config.putAll(Map.of(
            "micronaut.metrics.enabled", StringUtils.FALSE,
            "eclipsestore.dynamodb.storage.cache.table-name", "eclipsestore",
            "eclipsestore.cache.counter.key-type", "java.lang.String",
            "eclipsestore.cache.counter.value-type", "java.lang.Long",
            "eclipsestore.cache.counter.storage", "cache"
        ));

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
