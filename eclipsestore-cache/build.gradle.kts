plugins {
    id("io.micronaut.internal.build.eclipsestore-module")
}

// REMOVE ONCE FIRST RELEASE IS RELEASED
micronautBuild {
    binaryCompatibility {
        enabled.set(false)
    }
}

dependencies {
    api(projects.micronautEclipsestore)
    api(libs.managed.eclipsestore.cache)
    api(mnCache.micronaut.cache.core)
    testImplementation(mnCache.micronaut.cache.tck)
    testImplementation(libs.jupiter.api)
}
