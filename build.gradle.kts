import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath ("org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

plugins {
    id("com.github.ben-manes.versions") version "0.42.0"
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        fun isNonStable(version: String): Boolean {
            val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
            val regex = "^[0-9,.v-]+(-r)?$".toRegex()
            val isStable = stableKeyword || regex.matches(version)
            return isStable.not()
        }

        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
