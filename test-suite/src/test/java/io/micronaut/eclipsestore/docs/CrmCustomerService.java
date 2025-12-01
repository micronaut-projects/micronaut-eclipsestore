package io.micronaut.eclipsestore.docs;

import org.jspecify.annotations.NonNull;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CrmCustomerService {
    @NonNull
    Customers save(@NonNull @NotNull @Valid Customer customer);
}
