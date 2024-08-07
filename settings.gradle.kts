import io.micronaut.build.MicronautBuildSettingsExtension

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("io.micronaut.build.shared.settings") version "7.2.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "eclipsestore-parent"

include("eclipsestore-bom")
include("eclipsestore")
include("eclipsestore-annotations")
include("eclipsestore-cache")
include("eclipsestore-rest")
include("eclipsestore-processor")

include("test-suite")
include("test-suite-utils")
include("test-suite-groovy")
include("test-suite-kotlin")

configure<MicronautBuildSettingsExtension> {
    useStandardizedProjectNames.set(true)
    importMicronautCatalog()
    importMicronautCatalog("micronaut-aws")
    importMicronautCatalog("micronaut-cache")
    importMicronautCatalog("micronaut-micrometer")
    importMicronautCatalog("micronaut-serde")
    importMicronautCatalog("micronaut-sql")
    importMicronautCatalog("micronaut-test-resources")
    importMicronautCatalog("micronaut-validation")
}
