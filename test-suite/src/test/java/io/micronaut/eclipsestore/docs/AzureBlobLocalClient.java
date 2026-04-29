package io.micronaut.eclipsestore.docs;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;

import java.util.Map;

@Factory
@Requires(property = "azure.test", value = StringUtils.TRUE)
public class AzureBlobLocalClient {

    static BlobServiceClient createClient(Map<String, Object> properties) {
        return createClient(
            properties.get("endpoint").toString(),
            properties.get("connection-string").toString()
        );
    }

    @Singleton
    @Replaces(BlobServiceClient.class)
    BlobServiceClient buildClient(
        @Value("${endpoint}") String endpoint,
        @Value("${connection-string}") String connectionString
    ) {
        return createClient(endpoint, connectionString);
    }

    private static BlobServiceClient createClient(String endpoint, String connectionString) {
        return new BlobServiceClientBuilder()
            .endpoint(endpoint)
            .connectionString(connectionString)
            .buildClient();
    }
}
