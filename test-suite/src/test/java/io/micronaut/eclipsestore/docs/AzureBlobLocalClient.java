package io.micronaut.eclipsestore.docs;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;

@Factory
@Requires(property = "azure.test", value = StringUtils.TRUE)
public class AzureBlobLocalClient {

    @Singleton
    @Replaces(BlobServiceClient.class)
    BlobServiceClient buildClient() {
        return new BlobServiceClientBuilder()
            .endpoint("https://127.0.0.1:10000/devstoreaccount1")
            .connectionString("DefaultEndpointsProtocol=http;AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;")
            .buildClient();
    }
}
