plugins {
    id("io.micronaut.build.internal.java-base")
    id("io.micronaut.internal.build.eclipsestore-testsuite")
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

    testImplementation(libs.firestore.sdk)
    testImplementation(libs.managed.eclipsestore.google.firestore)

    testRuntimeOnly(libs.managed.eclipsestore.aws.dynamodb)
    testImplementation(libs.awssdk.dynamodb)

    // Azure connector tests
    implementation(platform(mnAzure.micronaut.azure.bom))
    testImplementation(libs.azuresdk.blob)
    testImplementation(libs.managed.eclipsestore.azure.storage)

    testImplementation(platform(mnTest.boms.testcontainers))
    testImplementation(libs.testcontainers.minio)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(projects.testSuiteUtils)
}

