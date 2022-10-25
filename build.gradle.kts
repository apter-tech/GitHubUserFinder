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
        classpath("com.squareup.sqldelight:gradle-plugin:${Versions.sqldelight}")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}")
        classpath("org.jetbrains.kotlinx:kover:${Versions.kover}")
    }
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        // preconfigure defaults
        buildUponDefaultConfig = true
        // point to your custom config defining rules to run, overwriting default behavior
        config = files("$rootDir/config/detekt.yml")
    }

    apply(plugin = "kover")
    kover {
        verify {
            rule {
                isEnabled = true
                name = "Minimum coverage verification error"
                target =
                    kotlinx.kover.api.VerificationTarget.ALL

                bound {
                    minValue = 60
                    maxValue = 100
                    counter =
                        kotlinx.kover.api.CounterType.LINE
                    valueType =
                        kotlinx.kover.api.VerificationValueType.COVERED_PERCENTAGE
                }
            }
        }
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = "1.8"
        reports {
            html.required.set(true)
            xml.required.set(true)
        }
    }

    tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
        jvmTarget = "1.8"
    }
    repositories {
        google()
        mavenCentral()
    }
    afterEvaluate {
        project.extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()
            ?.let { ext ->
                ext.sourceSets.removeAll { sourceSet ->
                    setOf(
                        "androidAndroidTestRelease",
                        "androidTestFixtures",
                        "androidTestFixturesDebug",
                        "androidTestFixturesRelease",
                    ).contains(sourceSet.name)
                }
            }
    }
}

subprojects {
    afterEvaluate {
        tasks.named("check").configure {
            dependsOn(tasks.getByName("detekt"))
        }
    }
}

plugins {
    id("com.github.ben-manes.versions") version Versions.benManesVersions
    id("io.gitlab.arturbosch.detekt") version Versions.detekt
    id("org.jetbrains.kotlinx.kover") version Versions.kover
}

koverMerged {
    enable()
    filters {
        projects {
            excludes += listOf("androidApp")
        }
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        fun isNonStable(version: String): Boolean {
            val stableKeyword =
                listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
            val regex = "^[0-9,.v-]+(-r)?$".toRegex()
            val isStable = stableKeyword || regex.matches(version)
            return isStable.not()
        }

        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
