plugins {
    id("com.android.application")
    kotlin("android")
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

    buildFeatures {
        compose = true
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
