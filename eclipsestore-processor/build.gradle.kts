plugins {
    id("io.micronaut.internal.build.eclipsestore-module")
}

dependencies {
    implementation(mn.micronaut.core.processor)
    implementation(projects.micronautEclipsestoreAnnotations)
}

// REMOVE ONCE FIRST RELEASE IS RELEASED
micronautBuild {
    binaryCompatibility {
        enabled.set(false)
    }
}
