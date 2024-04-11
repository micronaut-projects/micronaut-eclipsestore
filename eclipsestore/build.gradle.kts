import io.micronaut.testresources.buildtools.KnownModules.JDBC_POSTGRESQL

plugins {
    id("io.micronaut.internal.build.eclipsestore-module")
    id("io.micronaut.test-resources") version "4.3.6"
}

dependencies {
    compileOnly(mnMicrometer.micronaut.micrometer.core)

    implementation(platform(mnAws.micronaut.aws.bom))
    compileOnly(libs.awssdk.s3)
    compileOnly(libs.managed.eclipsestore.aws.s3)
    compileOnly(libs.managed.eclipsestore.aws.dynamodb)
    compileOnly(libs.awssdk.dynamodb)
    compileOnly(libs.managed.eclipsestore.sql)
    compileOnly(mnAws.micronaut.aws.sdk.v2)
    compileOnly(mn.micronaut.management)
    compileOnly(mnSql.micronaut.jdbc)

    api(libs.managed.eclipsestore.storage.embedded.configuration)
    api(projects.micronautEclipsestoreAnnotations)

    implementation(mn.reactor)
    implementation(libs.eclipsestore.persistence.binary.jdk8)
    implementation(libs.eclipsestore.persistence.binary.jdk17)


    testImplementation(mnSerde.micronaut.serde.jackson)
    testImplementation(mn.reactor.test)
    testImplementation(mnMicrometer.micronaut.micrometer.core)
    testImplementation(mn.micronaut.management)
    testImplementation(mn.micronaut.http.server.netty)
    testImplementation(mn.micronaut.http.client)

    testImplementation(projects.testSuiteUtils)

    // S3 connector tests
    testImplementation(libs.managed.eclipsestore.aws.s3)
    testImplementation(libs.awssdk.s3)
    testImplementation(mnAws.micronaut.aws.sdk.v2)
    testImplementation(platform(mnTestResources.boms.testcontainers))
    testImplementation(libs.testcontainers.minio)

    // Postgres connector tests
    testImplementation(libs.managed.eclipsestore.sql)
    testImplementation(mnSql.micronaut.jdbc.hikari)
    testRuntimeOnly(mnSql.postgresql)

    // DynamoDB tests
    testImplementation(libs.managed.eclipsestore.aws.dynamodb)
    testImplementation(libs.awssdk.dynamodb)

    testRuntimeOnly(mnLogging.logback.classic)
}

micronaut {
    importMicronautPlatform.set(false)
    testResources {
        enabled.set(true)
        additionalModules.add(JDBC_POSTGRESQL)
    }
}

