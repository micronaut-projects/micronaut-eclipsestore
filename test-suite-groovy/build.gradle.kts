plugins {
    id("groovy")
    id("io.micronaut.internal.build.eclipsestore-testsuite")
}

dependencies {
    testCompileOnly(mn.micronaut.inject.groovy)
    testCompileOnly(projects.micronautEclipsestoreProcessor)

    testImplementation(platform(mn.micronaut.core.bom))
    testImplementation(projects.micronautEclipsestore)
    testImplementation(projects.micronautEclipsestoreCache)
    testImplementation(mnValidation.micronaut.validation)
    testImplementation(mnSerde.micronaut.serde.jackson)
    testImplementation(mnTest.micronaut.test.spock)
    testImplementation(mn.micronaut.http.server.netty)
    testImplementation(mn.micronaut.http.client)

    testRuntimeOnly(mn.snakeyaml)
    testRuntimeOnly(mnLogging.logback.classic)
}
