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
package io.micronaut.eclipsestore.azure;

import com.azure.storage.blob.BlobServiceClient;
import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.inject.qualifiers.Qualifiers;
import jakarta.inject.Singleton;
import org.eclipse.store.afs.azure.storage.types.AzureStorageConnector;
import org.eclipse.store.afs.blobstore.types.BlobStoreFileSystem;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Simon Frauenschuh
 * @since 1.6.0
 */
@Factory
public class BlobStorageFoundationFactory {

    private static final Logger LOG = LoggerFactory.getLogger(BlobStorageFoundationFactory.class);

    /**
     * @param ctx      Bean Context.
     * @param provider A {@link BlobStorageConfigurationProvider} provider.
     * @return A {@link org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation}.
     */
    @Singleton
    @EachBean(BlobStorageConfigurationProvider.class)
    EmbeddedStorageFoundation<?> createFoundation(
        BlobStorageConfigurationProvider provider,
        BeanContext ctx
    ) {
        String storageAccountClientName = provider.getBlobClientName().orElse(provider.getName());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Looking for Azure Storage Account Client named '{}'", storageAccountClientName);
        }

        BlobServiceClient client = ctx.findBean(BlobServiceClient.class, Qualifiers.byName(storageAccountClientName))
            .orElseGet(() -> defaultClient(ctx, storageAccountClientName));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Got Azure Storage Account Client {}", client);
        }

        BlobStoreFileSystem fileSystem = BlobStoreFileSystem.New(
            AzureStorageConnector.Caching(client)
        );

        return EmbeddedStorage.Foundation(fileSystem.ensureDirectoryPath(provider.getContainerName()));
    }

    private BlobServiceClient defaultClient(BeanContext ctx, String storageAccountClientName) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("No Azure Storage Account Client named '{}' found. Looking for a default", storageAccountClientName);
        }
        return ctx.getBean(BlobServiceClient.class);
    }
}
