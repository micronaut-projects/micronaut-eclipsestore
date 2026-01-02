package io.micronaut.eclipsestore.docs;

import io.micronaut.core.util.StringUtils;
import io.micronaut.eclipsestore.testutils.Postgresql;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class PostgresCustomerControllerTest extends BaseCustomerControllerTest {

    @Override
    protected Map<String, Object> extraProperties() {
        Map<String, Object> result = new HashMap<>(Map.of(
            "datasources.main.db-type", "postgresql",
            "micronaut.metrics.enabled", StringUtils.FALSE,
            "eclipsestore.postgres.storage.main.table-name", "eclipsestore" + UUID.randomUUID(),
            "eclipsestore.postgres.storage.main.root-class", "io.micronaut.eclipsestore.docs.Data"
        ));
        Map<String, String> postgresProperties = Postgresql.getProperties("main");
        for (String k : postgresProperties.keySet()) {
            result.put(k, postgresProperties.get(k));
        }
        return result;
    }

    @EnabledIf("dockerAvailable")
    @ParameterizedTest
    @MethodSource("provideCustomerRepositoryImplementations")
    void testCrud(String customerRepositoryImplementation) throws Exception {
        super.verifyCrudWithEclipseStore(customerRepositoryImplementation);
    }
}
