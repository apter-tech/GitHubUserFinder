plugins {
    alias(libs.plugins.android.application)
    id(libs.plugins.kotlin.android.get().pluginId)
}

val androidNameSpace = "io.imrekaszab.githubuserfinder.android"
android {
    namespace = androidNameSpace
    compileSdk = libs.versions.targetSdk.get().toInt()
    defaultConfig {
        applicationId = "io.imrekaszab.githubuserfinder.android"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_PATH", "\"https://api.github.com\"")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(
            "VERSION_" + libs.versions.javaSourceCompatibility.get().replace(".", "_")
        )
        targetCompatibility = JavaVersion.valueOf(
            "VERSION_" + libs.versions.javaTargetCompatibility.get().replace(".", "_")
        )
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.javaTargetCompatibility.get().toInt()))
    }
}

dependencies {
    kover(project(":shared"))
    implementation(project(":shared"))

    // AndroidX
    implementation(libs.androidx.appcompat)

    // Compose
    debugImplementation(libs.compose.tooling)
    implementation(libs.bundles.compose)

    // DI
    implementation(libs.bundles.koinAndroid)

    // Detekt
    detektPlugins(libs.detekt.formatting)
}

val defaultRequiredMinimumCoverage = 80
val defaultRequiredMaximumCoverage = 100

koverReport {
    filters {
        excludes {
            classes("$androidNameSpace.*")
        }
    }
    androidReports("debug") {
        verify {
            rule("Minimum coverage verification error") {
                isEnabled = true
                entity = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.APPLICATION

                bound {
                    minValue = defaultRequiredMinimumCoverage
                    maxValue = defaultRequiredMaximumCoverage
                    metric = kotlinx.kover.gradle.plugin.dsl.MetricType.LINE
                    aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
                }
            }
        }
    }
}
