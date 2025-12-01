package io.micronaut.eclipsestore.docs;

import org.jspecify.annotations.NonNull;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface CustomerRepository {

    @NonNull
    Customer save(@NonNull @NotNull @Valid CustomerSave customerSave);

    void update(@NonNull @NotBlank String id,
                @NonNull @NotNull @Valid CustomerSave customerSave);

    @NonNull
    Optional<Customer> findById(@NonNull @NotBlank String id);

    void deleteById(@NonNull @NotBlank String id);
}
