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

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import java.util.Optional;

/**
 * @author Simon Frauenschuh
 * @since 1.7.0
 */
@Internal
@EachProperty("eclipsestore.firestore.storage")
class DefaultFirestoreStorageConfigurationProvider implements FirestoreStorageConfigurationProvider {

    @NonNull
    private Class<?> rootClass;

    private final String name;

    @Nullable
    private String firestoreClientName;

    @NonNull
    private String logicalDirectory;

    public DefaultFirestoreStorageConfigurationProvider(@Parameter String name) {
        this.name = name;
    }

    @Override
    @NonNull
    public String getName() {
        return name;
    }

    @Override
    @NonNull
    public Class<?> getRootClass() {
        return this.rootClass;
    }

    /**
     * Class of the Root Instance.
     * <a href="https://docs.eclipsestore.io/manual/storage/root-instances.html">Root Instances</a>
     * @param rootClass Class for the Root Instance.
     */
    public void setRootClass(@NonNull Class<?> rootClass) {
        this.rootClass = rootClass;
    }

    @Override
    @NonNull
    public Optional<String> getFirestoreClientName() {
        return Optional.ofNullable(firestoreClientName);
    }

    /**
     * The name qualifier of the defined Firestore to use.
     * If unset, a Firestore with the same name as the storage will be used.
     * If there is no bean with a name qualifier matching the storage name, the default client will be used.
     *
     * @param firestoreClientName the name qualifier of the S3Client to use
     */
    public void setFirestoreClientName(@Nullable String firestoreClientName) {
        this.firestoreClientName = firestoreClientName;
    }

    @NonNull
    public String getLogicalDirectory() {
        return logicalDirectory;
    }

    /**
     * @param logicalDirectory Name of (logical) Firestore directory.
     */
    public void setLogicalDirectory(@NonNull String logicalDirectory) {
        this.logicalDirectory = logicalDirectory;
    }
}
