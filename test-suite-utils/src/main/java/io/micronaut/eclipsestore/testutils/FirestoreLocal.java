package io.micronaut.eclipsestore.testutils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

public class FirestoreLocal {
    GenericContainer firebaseLocal;

    public FirestoreLocal() {
        List<String> portBindings = new ArrayList<>();
        portBindings.add("8080:8080");
        firebaseLocal = new GenericContainer(DockerImageName.parse("seriousben/cloud-firestore"));
        firebaseLocal.addEnv("PROJECT_ID", "dummyid");
        firebaseLocal.addEnv("FIRESTORE_EMULATOR_HOST", "localhost:8080");
        firebaseLocal.setPortBindings(portBindings);
        firebaseLocal.start();
    }

    public void close() {
        firebaseLocal.close();
    }

    public static Firestore firestoreClient() throws InterruptedException {
        // The used Firestore docker image needs quite a boot-time, so this was the easiest fix.
        Thread.sleep(2000);

        // Has to be set, otherwise the client instantiates correctly but fails when requests are tried to sen
        System.setProperty("FIRESTORE_EMULATOR_HOST", "localhost:8080");

        GoogleCredentials credentials = GoogleCredentials.newBuilder()
            .build();
        FirestoreOptions options = FirestoreOptions.getDefaultInstance().toBuilder()
            .setCredentials(credentials)
            .setHost("localhost:8080")
            .setProjectId("dummyid")
            .build();

        return options.getService();
    }
}
