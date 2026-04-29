package io.micronaut.eclipsestore.docs;

import com.azure.storage.blob.BlobServiceClient;
import io.micronaut.core.util.StringUtils;
import io.micronaut.eclipsestore.testutils.AzureBlobLocal;
import org.junit.jupiter.api.AfterAll;
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

    private static final AzureBlobLocal AZURE_BLOB_LOCAL = new AzureBlobLocal();

    private static Map<String, Object> azureBlobProperties;

    @Override
    protected Map<String, Object> extraProperties() {
        Map<String, Object> properties = new HashMap<>(azureBlobProperties());
        properties.putAll(Map.of(
            "azure.test", StringUtils.TRUE,
            "eclipsestore.blob.storage.main.container-name", CONTAINER_NAME,
            "eclipsestore.blob.storage.main.root-class", "io.micronaut.eclipsestore.docs.Data",
            "micronaut.metrics.enabled", StringUtils.FALSE
        ));
        return properties;
    }

    @BeforeEach
    public void initBlobClient() {
        BlobServiceClient blobServiceClient = AzureBlobLocalClient.createClient(azureBlobProperties());
        blobServiceClient.createBlobContainerIfNotExists(CONTAINER_NAME);
    }

    @AfterAll
    static void closeBlobLocal() {
        AZURE_BLOB_LOCAL.close();
    }

    private static synchronized Map<String, Object> azureBlobProperties() {
        if (azureBlobProperties == null) {
            azureBlobProperties = AzureBlobLocal.getProperties();
        }
        return azureBlobProperties;
    }

    @EnabledIf("dockerAvailable")
    @ParameterizedTest
    @MethodSource("provideCustomerRepositoryImplementations")
    void testCrud(String customerRepositoryImplementation) throws Exception {
        super.verifyCrudWithEclipseStore(customerRepositoryImplementation);
    }
}
