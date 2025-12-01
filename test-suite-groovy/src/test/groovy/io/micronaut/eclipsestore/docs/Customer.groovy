package io.micronaut.eclipsestore.docs

import io.micronaut.core.annotation.Introspected
import org.jspecify.annotations.NonNull
import org.jspecify.annotations.Nullable

import jakarta.validation.constraints.NotBlank

@Introspected
class Customer {
    @NonNull
    @NotBlank
    String id

    @NonNull
    @NotBlank
    String firstName

    @Nullable
    String lastName

    Customer(@NonNull String id, @NonNull String firstName, @Nullable String lastName) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
    }
}
