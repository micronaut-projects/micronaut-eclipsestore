package io.micronaut.eclipsestore.docs

import io.micronaut.core.annotation.Introspected
import org.jspecify.annotations.NonNull
import org.jspecify.annotations.Nullable
import jakarta.validation.constraints.NotBlank

@Introspected
class CustomerSave {

    @NonNull
    @NotBlank
    String firstName

    @Nullable
    String lastName
}



