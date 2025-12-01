package io.micronaut.eclipsestore.rest

import org.jspecify.annotations.NonNull
import io.micronaut.serde.annotation.Serdeable

@Serdeable
class People {

    @NonNull
    List<String> people = []
}
