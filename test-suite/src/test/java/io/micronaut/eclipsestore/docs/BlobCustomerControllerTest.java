package io.micronaut.eclipsestore.docs;

import com.azure.storage.blob.BlobServiceClient;
import io.micronaut.core.util.StringUtils;
import io.micronaut.eclipsestore.testutils.AzureBlobLocal;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
class BlobCustomerControllerTest extends BaseCustomerControllerTest {
    private static final String CONTAINER_NAME = "devstoreaccount1";

    static AzureBlobLocal azureBlobLocal = new AzureBlobLocal();

    @Override
    protected Map<String, Object> extraProperties() {
        Map<String, Object> properties = new HashMap<>(AzureBlobLocal.getProperties());
        properties.putAll(Map.of(
            "azure.test", StringUtils.TRUE,
            "eclipsestore.blob.storage.main.container-name", CONTAINER_NAME,
            "eclipsestore.blob.storage.main.root-class", "io.micronaut.eclipsestore.docs.Data",
            "micronaut.metrics.enabled", StringUtils.FALSE
        ));
        return properties;
    }

    @Inject
    BlobServiceClient blobServiceClient;

    @BeforeEach
    public void initBlobClient() {
        blobServiceClient = new AzureBlobLocalClient().buildClient();
        blobServiceClient.createBlobContainerIfNotExists("devstoreaccount1");
    }

    @EnabledIf("dockerAvailable")
    @ParameterizedTest
    @MethodSource("provideCustomerRepositoryImplementations")
    void testCrud(String customerRepositoryImplementation) throws Exception {
        super.verifyCrudWithEclipseStore(customerRepositoryImplementation);
    }
}
