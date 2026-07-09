package io.micronaut.eclipsestore.testutils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Map;

public class FirestoreLocal {
    private static final int FIRESTORE_PORT = 8080;
    private static volatile String emulatorHost;

    private final GenericContainer<?> firebaseLocal;

    public FirestoreLocal() {
        firebaseLocal = new GenericContainer<>(DockerImageName.parse("seriousben/cloud-firestore"))
            .withEnv("PROJECT_ID", "dummyid")
            .withExposedPorts(FIRESTORE_PORT)
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(60)));
        start();
    }

    public void close() {
        firebaseLocal.close();
    }

    public Map<String, String> getProperties() {
        start();
        return Map.of();
    }

    public static Firestore firestoreClient() {
        String host = emulatorHost;
        if (host == null) {
            throw new IllegalStateException("Firestore emulator has not been started");
        }
        // Has to be set, otherwise the client instantiates correctly but fails when requests are tried to send.
        System.setProperty("FIRESTORE_EMULATOR_HOST", host);

        GoogleCredentials credentials = GoogleCredentials.newBuilder()
            .build();
        FirestoreOptions options = FirestoreOptions.getDefaultInstance().toBuilder()
            .setCredentials(credentials)
            .setEmulatorHost(host)
            .setProjectId("dummyid")
            .build();

        return options.getService();
    }

    private void start() {
        if (!firebaseLocal.isRunning()) {
            firebaseLocal.start();
        }
        emulatorHost = hostAddress();
    }

    private String hostAddress() {
        String host = firebaseLocal.getHost();
        if ("localhost".equals(host)) {
            host = "127.0.0.1";
        }
        return host + ":" + firebaseLocal.getMappedPort(FIRESTORE_PORT);
    }
}
