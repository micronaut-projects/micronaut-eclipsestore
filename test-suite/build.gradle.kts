plugins {
    id("java-library")
    id("io.micronaut.internal.build.eclipsestore-testsuite")
    id("io.micronaut.test-resources") version "4.2.0"
}

dependencies {
    testAnnotationProcessor(platform(mn.micronaut.core.bom))
    testAnnotationProcessor(projects.micronautEclipsestoreProcessor)
    testAnnotationProcessor(mn.micronaut.inject.java)
    testAnnotationProcessor(mnValidation.micronaut.validation.processor)
    testAnnotationProcessor(mnSerde.micronaut.serde.processor)

    testImplementation(platform(mn.micronaut.core.bom))
    testImplementation(mnValidation.micronaut.validation)
    testImplementation(mnSerde.micronaut.serde.jackson)

    testImplementation(projects.micronautEclipsestoreCache)
    testImplementation(projects.micronautEclipsestore)
    testImplementation(projects.micronautEclipsestoreRest)

    testImplementation(libs.jupiter.api)
    testImplementation(mnTest.micronaut.test.junit5)
    testRuntimeOnly(libs.junit.jupiter.engine)

    testRuntimeOnly(mnLogging.logback.classic)
    testRuntimeOnly(mn.snakeyaml)

    testImplementation(mn.micronaut.http.server.netty)
    testImplementation(mn.micronaut.http.client)
    testImplementation(libs.jupiter.jupiter.params)

    testImplementation(libs.managed.eclipsestore.sql)
    testImplementation(mnSql.micronaut.jdbc.hikari)
    testRuntimeOnly(mnSql.postgresql)

    testImplementation(libs.managed.eclipsestore.aws.s3)
    testImplementation(libs.awssdk.s3)
    testImplementation(mnAws.micronaut.aws.sdk.v2)

    testRuntimeOnly(libs.managed.eclipsestore.aws.dynamodb)
    testImplementation(libs.awssdk.dynamodb)

    testImplementation(platform(mnTestResources.boms.testcontainers))
    testImplementation(libs.testcontainers.minio)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(projects.testSuiteUtils)
}

micronaut {
    importMicronautPlatform.set(false)
    testResources {
        enabled.set(true)
        additionalModules.add("jdbc-postgresql")
    }
}
