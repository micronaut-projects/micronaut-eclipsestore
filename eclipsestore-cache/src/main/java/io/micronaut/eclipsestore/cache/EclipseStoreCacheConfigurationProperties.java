/*
 * Copyright 2017-2024 original authors
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
package io.micronaut.eclipsestore.cache;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

/**
 * @param <K> Key Type
 * @param <V> Value Type
 * @author Sergio del Amo
 * @since 1.3.0
 */
@EachProperty(EclipseStoreCacheConfigurationProperties.PREFIX)
public class EclipseStoreCacheConfigurationProperties<K, V> implements EclipseStoreCacheConfiguration<K, V> {

    public static final String PREFIX = "eclipsestore.cache";

    /**
     * Whether this cache is enabled by default.
     */
    @SuppressWarnings("WeakerAccess")
    public static final boolean DEFAULT_ENABLED = true;
    private final String name;
    private boolean enabled = DEFAULT_ENABLED;
    @Nullable
    private Class<K> keyType;
    @Nullable
    private Class<V> valueType;
    @Nullable
    private String storage;
    @Nullable
    private Boolean readThrough;
    @Nullable
    private Boolean writeThrough;
    @Nullable
    private Boolean storeByValue;
    @Nullable
    private Boolean statisticsEnabled;
    @Nullable
    private Boolean managementEnabled;

    public EclipseStoreCacheConfigurationProperties(@Parameter String name) {
        this.name = name;
    }

    @Override
    @NonNull
    public Class<K> getKeyType() {
        return keyType != null ? keyType : (Class<K>) Object.class;
    }

    /**
     * @param keyType The required type of keys for the Cache.
     */
    public void setKeyType(@Nullable Class<K> keyType) {
        this.keyType = keyType;
    }

    @Override
    @NonNull
    public Class<V> getValueType() {
        return valueType != null ? valueType : (Class<V>) Object.class;
    }

    /**
     * @param valueType Determines type of values for the Cache.
     */
    public void setValueType(@Nullable Class<V> valueType) {
        this.valueType = valueType;
    }

    @Nullable
    @Override
    public String getStorage() {
        return storage;
    }

    /**
     * @param storage Name qualifier for EclipseStore Storage Manager
     */
    public void setStorage(@Nullable String storage) {
        this.storage = storage;
    }

    @Override
    @Nullable
    public Boolean isReadThrough() {
        return readThrough;
    }

    @Override
    @Nullable
    public Boolean isWriteThrough() {
        return writeThrough;
    }

    @Override
    @Nullable
    public Boolean isStoreByValue() {
        return storeByValue;
    }

    @Override
    @Nullable
    public Boolean isStatisticsEnabled() {
        return statisticsEnabled;
    }

    @Override
    @Nullable
    public Boolean isManagementEnabled() {
        return managementEnabled;
    }

    @Override
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * When in "read-through" mode, cache misses that occur due to cache entries not existing as a result of performing a "get" will appropriately cause the configured CacheLoader to be invoked. When you set a Storage Manager, "read-through" mode is activated.
     *
     * @param readThrough Whether to use "read-through" mode
     */
    public void setReadThrough(Boolean readThrough) {
        this.readThrough = readThrough;
    }

    /**
     * When in "write-through" mode, cache updates that occur as a result of performing "put" operations will appropriately cause the configured CacheWriter to be invoked.When you set a Storage Manager, "write-through" mode is activated.
     *
     * @param writeThrough Whether to use "write-through" mode.
     */
    public void setWriteThrough(Boolean writeThrough) {
        this.writeThrough = writeThrough;
    }

    /**
     * When a cache is storeByValue, any mutation to the key or value does not affect the key of value stored in the cache.
     *
     * @param storeByValue When a cache is storeByValue, any mutation to the key or value does not affect the key of value stored in the cache.
     */
    public void setStoreByValue(Boolean storeByValue) {
        this.storeByValue = storeByValue;
    }

    /**
     * Whether statistics collection is enabled in this cache.
     *
     * @param statisticsEnabled Whether statistics collection is enabled in this cache.
     */
    public void setStatisticsEnabled(Boolean statisticsEnabled) {
        this.statisticsEnabled = statisticsEnabled;
    }

    /**
     * Whether management is enabled on this cache.
     *
     * @param managementEnabled Whether management is enabled on this cache.
     */
    public void setManagementEnabled(Boolean managementEnabled) {
        this.managementEnabled = managementEnabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Whether this cache is enabled. Default Value: {@value #DEFAULT_ENABLED}
     *
     * @param enabled Whether this cache is enabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
