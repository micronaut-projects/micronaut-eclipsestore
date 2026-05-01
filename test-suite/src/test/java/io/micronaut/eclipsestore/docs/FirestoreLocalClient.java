package io.micronaut.eclipsestore.docs;

import com.google.cloud.firestore.Firestore;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import io.micronaut.eclipsestore.testutils.FirestoreLocal;
import jakarta.inject.Singleton;

@Factory
@Requires(property = "firestore.test", value = StringUtils.TRUE)
public class FirestoreLocalClient {

    @Singleton
    @Replaces(Firestore.class)
    Firestore buildClient() throws InterruptedException {
        return FirestoreLocal.firestoreClient();
    }
}
