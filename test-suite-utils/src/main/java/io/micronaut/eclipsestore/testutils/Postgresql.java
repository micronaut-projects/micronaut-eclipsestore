package io.micronaut.eclipsestore.testutils;

import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

/**
 * @see <a href="https://testcontainers.com/modules/postgresql/">postgresql TestContainers</a>
 */
public class Postgresql {
    private static final String IMAGE_NAME = "postgres";
    public static final String DEFAULT_DATASOURCE_NAME = "default";
    private static PostgreSQLContainer container;

    public static Map<String, String> getProperties() {
        return getProperties(DEFAULT_DATASOURCE_NAME);
    }

    public static Map<String, String> getProperties(String datasourceName) {
        return getProperties(getContainer(), datasourceName);
    }

    private static PostgreSQLContainer getContainer() {
        if (container == null) {
            container = new PostgreSQLContainer(DockerImageName.parse(IMAGE_NAME));
            container.start();
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (!container.isRunning());
            return container;
        }
        return container;
    }

    private static Map<String, String> getProperties(PostgreSQLContainer container,
                                                     String datasourceName) {
        return Map.of(
            "datasources." + datasourceName + ".url", container.getJdbcUrl(),
            "datasources." + datasourceName + ".username", container.getUsername(),
            "datasources." + datasourceName + ".password", container.getPassword(),
            "datasources." + datasourceName + ".db-type", "postgres",
            "datasources." + datasourceName + ".driver-class-name", "org.postgresql.Driver"
        );
    }
}
