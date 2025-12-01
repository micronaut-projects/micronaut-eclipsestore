package io.micronaut.eclipsestore.docs;

import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private Map<String, Customer> customers = new HashMap<>();

    @NonNull
    public Map<String, Customer> getCustomers() {
        return this.customers;
    }
}
