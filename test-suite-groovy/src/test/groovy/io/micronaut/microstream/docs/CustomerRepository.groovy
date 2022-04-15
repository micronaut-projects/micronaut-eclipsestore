package io.micronaut.microstream.docs

import io.micronaut.core.annotation.NonNull

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

interface CustomerRepository {

    void save(@NonNull @NotNull @Valid Customer customer)

    Collection<Customer> findByFirstName(@NonNull @NotBlank String firstName)

    @NonNull
    Optional<Customer> findById(@NonNull @NotBlank String id)

    void deleteById(@NonNull @NotBlank String id)
}
