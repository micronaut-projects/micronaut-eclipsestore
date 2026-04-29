package io.micronaut.eclipsestore.testutils;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import java.util.Map;

import static java.lang.Thread.sleep;

public class AzureBlobLocal {
    private static final DockerImageName AZURITE_IMAGE = DockerImageName.parse("mcr.microsoft.com/azure-storage/azurite:3.35.0@sha256:647c63a91102a9d8e8000aab803436e1fc85fbb285e7ce830a82ee5d6661cf37");
    private static GenericContainer azuriteContainer = new GenericContainer(AZURITE_IMAGE)
        .withCommand("azurite-blob", "--blobHost", "0.0.0.0", "--skipApiVersionCheck")
        .withExposedPorts(10000);

    public static Map<String, Object> getProperties() {
        try {
            do {
                if (!azuriteContainer.isRunning()) {
                    azuriteContainer.start();
                }
                sleep(500);
            } while (!azuriteContainer.isRunning());
            String endpoint = "http://127.0.0.1:" + azuriteContainer.getMappedPort(10000) + "/devstoreaccount1";
            return Map.of(
                "endpoint", endpoint,
                "connection-string", "DefaultEndpointsProtocol=http;AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;BlobEndpoint=" + endpoint +";",
                "blob-container", "devstoreaccount1",
                "azure.credential.storage-shared-key.account-name", "devstoreaccount1",
                "azure.credential.storage-shared-key.account-key", "Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw=="
            );
        } catch (InterruptedException e) {
            return Map.of();
        }
    }

    public void close() {
        azuriteContainer.close();
    }
}
