pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        maven {
            setUrl("https://androidx.dev/storage/compose-compiler/repository/")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "GitHubUserFinder"
include(":androidApp")
include(":shared")
