package io.micronaut.eclipsestore.docs

import io.micronaut.core.annotation.Introspected

@Introspected
class Data {
    Map<String, Customer> customers = [:]
}
