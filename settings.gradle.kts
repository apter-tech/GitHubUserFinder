pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "GitHubUserFinder"
include(":androidApp")
include(":shared")

enableFeaturePreview("VERSION_CATALOGS")
