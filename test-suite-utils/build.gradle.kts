plugins {
    id("java-library")
    id("io.micronaut.build.internal.common")
    id("io.micronaut.internal.build.eclipsestore-base")
}

dependencies {
    implementation(platform(mnTestResources.boms.testcontainers))
    implementation(libs.testcontainers.minio)
    implementation(libs.managed.eclipsestore.aws.s3)
    implementation(platform(mnAws.micronaut.aws.bom))
    implementation(libs.awssdk.s3)
    implementation(libs.firestore.sdk)
}

spotless {
    java {
        targetExclude("**/testutils/**")
    }
}

tasks.withType<Checkstyle> {
    enabled = false
}
