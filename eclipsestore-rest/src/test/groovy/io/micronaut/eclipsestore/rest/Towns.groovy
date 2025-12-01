package io.micronaut.eclipsestore.rest

import org.jspecify.annotations.NonNull
import io.micronaut.serde.annotation.Serdeable

@Serdeable
class Towns {

    @NonNull
    List<String> towns = []
}
