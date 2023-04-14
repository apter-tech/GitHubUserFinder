plugins {
    alias(libs.plugins.android.application)
    id(libs.plugins.kotlin.android.get().pluginId)
}

android {
    namespace = "io.imrekaszab.githubuserfinder.android"
    compileSdk = libs.versions.targetSdk.get().toInt()
    defaultConfig {
        applicationId = "io.imrekaszab.githubuserfinder.android"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_PATH", "\"https://api.github.com\"")
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }

    @Suppress("UnstableApiUsage")
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    @Suppress("UnstableApiUsage")
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
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
