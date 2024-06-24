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
/**
 * EclipseStore Storage Target support for Azure Blob Storage.
 *
 * @since 1.6.0
 * @author Simon Frauenschuh
 */
@Requires(classes = AzureStorageConnector.class)
@Requires(beans = BlobClient.class)
package io.micronaut.eclipsestore.azure;

import io.micronaut.context.annotation.Requires;
import org.eclipse.store.afs.azure.storage.types.AzureStorageConnector;
import com.azure.storage.blob.BlobClient;
