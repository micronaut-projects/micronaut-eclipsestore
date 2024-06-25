package io.micronaut.eclipsestore.testutils;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AzureBlobLocal {

    GenericContainer blobLocal;

    public AzureBlobLocal() {
        List<String> portBindings = new ArrayList<>();
        portBindings.add("10000:10000");
        portBindings.add("10001:10001");
        portBindings.add("10002:10002");
        blobLocal = new GenericContainer(DockerImageName.parse("mcr.microsoft.com/azure-storage/azurite"));
        blobLocal.setPortBindings(portBindings);
        blobLocal.start();
    }

    public static Map<String, String> getProperties() {
        return Map.of(
            "endpoint", "https://127.0.0.1:10000/devstoreaccount1",
            "connection-string", "DefaultEndpointsProtocol=http;AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;",
            "blob-container", "devstoreaccount1"
        );
    }
}
