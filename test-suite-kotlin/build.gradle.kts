plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("io.micronaut.internal.build.eclipsestore-testsuite")
}

dependencies {
    kaptTest(platform(mn.micronaut.core.bom))
    kaptTest(projects.micronautEclipsestoreProcessor)
    kaptTest(mnSerde.micronaut.serde.processor)
    kaptTest(mn.micronaut.inject.java)

    testImplementation(projects.micronautEclipsestore)
    testImplementation(projects.micronautEclipsestoreCache)

    testImplementation(libs.kotlin.stdlib)
    testImplementation(libs.jupiter.api)
    testImplementation(libs.jupiter.jupiter.params)

    testImplementation(mn.micronaut.http.client)
    testImplementation(mn.micronaut.http.server.netty)
    testImplementation(mnSerde.micronaut.serde.jackson)
    testImplementation(mnTest.micronaut.test.junit5)
    testImplementation(mnValidation.micronaut.validation)
    testImplementation(platform(mn.micronaut.core.bom))

    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(mn.snakeyaml)
    testRuntimeOnly(mnLogging.logback.classic)
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
