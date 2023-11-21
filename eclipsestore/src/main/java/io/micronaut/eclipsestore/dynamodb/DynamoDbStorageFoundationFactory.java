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
package io.micronaut.eclipsestore.dynamodb;

import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.inject.qualifiers.Qualifiers;
import jakarta.inject.Singleton;
import org.eclipse.store.afs.aws.dynamodb.types.DynamoDbConnector;
import org.eclipse.store.afs.blobstore.types.BlobStoreFileSystem;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Factory for an S3 based EmbeddedStorageFoundation.
 *
 * @author Tim Yates
 * @since 2.0.0
 */
@Factory
public class DynamoDbStorageFoundationFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DynamoDbStorageFoundationFactory.class);

    /**
     * @param ctx      Bean Context.
     * @param provider A {@link DynamoDbStorageConfigurationProvider} provider.
     * @return A {@link EmbeddedStorageFoundation}.
     */
    @Singleton
    @EachBean(DynamoDbStorageConfigurationProvider.class)
    EmbeddedStorageFoundation<?> createFoundation(
        DynamoDbStorageConfigurationProvider provider,
        BeanContext ctx
    ) {
        String dynamoDbClientName = provider.getDynamoDbClientName().orElse(provider.getName());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Looking for DynamoDbClient named '{}'", dynamoDbClientName);
        }

        DynamoDbClient dynamoDbclient = ctx.findBean(DynamoDbClient.class, Qualifiers.byName(dynamoDbClientName))
            .orElseGet(() -> defaultClient(ctx, dynamoDbClientName));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Got DynamoDbClient {}", dynamoDbclient);
        }

        BlobStoreFileSystem fileSystem = BlobStoreFileSystem.New(
            DynamoDbConnector.Caching(dynamoDbclient)
        );

        return EmbeddedStorage.Foundation(fileSystem.ensureDirectoryPath(provider.getTableName()));
    }

    private DynamoDbClient defaultClient(BeanContext ctx, String dynamoDbClientName) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("No DynamoDbClient named '{}' found. Looking for a default", dynamoDbClientName);
        }
        return ctx.getBean(DynamoDbClient.class);
    }
}
