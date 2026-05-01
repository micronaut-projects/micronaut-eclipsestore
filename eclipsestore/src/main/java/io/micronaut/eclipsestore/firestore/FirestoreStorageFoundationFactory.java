/*
 * Copyright 2017-2023 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.eclipsestore.firestore;

import com.google.cloud.firestore.Firestore;
import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.annotation.Internal;
import io.micronaut.inject.qualifiers.Qualifiers;
import jakarta.inject.Singleton;
import org.eclipse.store.afs.blobstore.types.BlobStoreFileSystem;
import org.eclipse.store.afs.googlecloud.firestore.types.GoogleCloudFirestoreConnector;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for an Firestore based EmbeddedStorageFoundation.
 *
 * @since 1.7.0
 * @author Simon Frauenschuh
 */
@Internal
@Factory
class FirestoreStorageFoundationFactory {

    private static final Logger LOG = LoggerFactory.getLogger(FirestoreStorageFoundationFactory.class);

    /**
     * @param ctx      Bean Context.
     * @param provider A {@link FirestoreStorageConfigurationProvider} provider.
     * @return A {@link EmbeddedStorageFoundation}.
     */
    @Singleton
    @EachBean(FirestoreStorageConfigurationProvider.class)
    EmbeddedStorageFoundation<?> createFoundation(
        FirestoreStorageConfigurationProvider provider,
        BeanContext ctx
    ) {
        String firestoreClientName = provider.getFirestoreClientName().orElse(provider.getName());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Looking for Firestore (client) named '{}'", firestoreClientName);
        }

        Firestore firestore = ctx.findBean(Firestore.class, Qualifiers.byName(firestoreClientName))
            .orElseGet(() -> defaultClient(ctx, firestoreClientName));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Got Firestore (client) {}", firestore);
        }

        BlobStoreFileSystem fileSystem = BlobStoreFileSystem.New(
            GoogleCloudFirestoreConnector.Caching(firestore)
        );

        return EmbeddedStorage.Foundation(fileSystem.ensureDirectoryPath(provider.getLogicalDirectory()));
    }

    private Firestore defaultClient(BeanContext ctx, String firestoreClientName) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("No Firestore (client) named '{}' found. Looking for a default", firestoreClientName);
        }
        return ctx.getBean(Firestore.class);
    }
}
