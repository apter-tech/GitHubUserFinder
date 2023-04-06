plugins {
    id("io.gitlab.arturbosch.detekt") version libs.versions.detekt.get()
    id("org.jetbrains.kotlinx.kover") version libs.versions.kover.get()
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
        classpath("com.android.tools.build:gradle:${libs.versions.agp.get()}")
        classpath("com.squareup.sqldelight:gradle-plugin:${libs.versions.sqldelight.get()}")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${libs.versions.detekt.get()}")
        classpath("org.jetbrains.kotlinx:kover:${libs.versions.kover.get()}")
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
                    minValue = 80
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

koverMerged {
    enable()
    filters {
        projects {
            excludes += listOf("androidApp")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
