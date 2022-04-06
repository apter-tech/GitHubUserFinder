plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = Versions.targetsdk
    defaultConfig {
        applicationId = "io.imrekaszab.githubuserfinder.android"
        minSdk = Versions.minsdk
        targetSdk = Versions.targetsdk
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
}