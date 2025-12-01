package io.micronaut.eclipsestore.docs;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

@Serdeable
public class CustomerSave {

    @NonNull
    @NotBlank
    private String firstName;

    @Nullable
    private String lastName;

    public CustomerSave(@NonNull String firstName, @Nullable String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }
}



