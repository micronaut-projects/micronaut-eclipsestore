package io.micronaut.eclipsestore.docs;

import io.micronaut.core.util.StringUtils;
import io.micronaut.eclipsestore.testutils.AzureBlobLocal;
import io.micronaut.eclipsestore.testutils.FirestoreLocal;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
class FirebaseCustomerControllerTest extends BaseCustomerControllerTest {
    private static final String LOGICAL_DIRECTORY = "eclipsestore";

    static FirestoreLocal firestoreLocal = new FirestoreLocal();

    @Override
    protected Map<String, Object> extraProperties() {
        Map<String, Object> properties = new HashMap<>(AzureBlobLocal.getProperties());
        properties.putAll(Map.of(
            "firestore.test", StringUtils.TRUE,
            "eclipsestore.firestore.storage.main.logical-directory", LOGICAL_DIRECTORY,
            "eclipsestore.firestore.storage.main.root-class", "io.micronaut.eclipsestore.docs.Data",
            "micronaut.metrics.enabled", StringUtils.FALSE
        ));
        return properties;
    }

    @EnabledIf("dockerAvailable")
    @ParameterizedTest
    @MethodSource("provideCustomerRepositoryImplementations")
    void testCrud(String customerRepositoryImplementation) throws Exception {
        super.verifyCrudWithEclipseStore(customerRepositoryImplementation);
    }
}
