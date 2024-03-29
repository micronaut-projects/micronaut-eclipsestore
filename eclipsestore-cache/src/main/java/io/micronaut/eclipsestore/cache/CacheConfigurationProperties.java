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

import io.micronaut.context.annotation.ConfigurationProperties;

/**
 * Configuration for EclipseStore Cache module.
 * {@link CacheConfiguration} and {@link CacheConfigurationProperties} exist to generate configuration reference documentation automatically.
 * It uses a different class because {@link EclipseStoreCacheConfigurationProperties} is a {@link io.micronaut.context.annotation.EachProperty} implementation.
 *
 * @author Sergio del Amo
 * @since 1.3.0
 */
@ConfigurationProperties(EclipseStoreCacheConfigurationProperties.PREFIX)
public class CacheConfigurationProperties implements CacheConfiguration {

    /**
     * Whether EclipseStore Cache module is enabled.
     */
    @SuppressWarnings("WeakerAccess")
    public static final boolean DEFAULT_ENABLED = true;

    private boolean enabled = DEFAULT_ENABLED;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Whether EclipseStore Cache module is enabled. Default Value: {@value CacheConfigurationProperties#DEFAULT_ENABLED}
     *
     * @param enabled Whether this cache is enabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
