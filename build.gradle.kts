plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.kover)
}

val koverExcludeList = listOf(
    "*.BuildConfig",
    "*.Mock*",
     "*.TestUtilAndroidKt",
    "io.imrekaszab.githubuserfinder.db.*",
    "*.di.*",
)

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
        filters {
            classes {
                excludes += koverExcludeList
            }
        }
        verify {
            rule {
                isEnabled = true
                name = "Minimum coverage verification error"
                target =
                    kotlinx.kover.api.VerificationTarget.ALL

                bound {
                    minValue = 90
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
        classes {
            excludes += koverExcludeList
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
