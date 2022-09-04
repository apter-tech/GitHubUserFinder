plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.rickclephas.kmp.nativecoroutines") version Versions.kmpNativeCoroutines
}

kotlin {
    android()
    ios()
    // Note: iosSimulatorArm64 target requires that all dependencies have M1 support
    iosSimulatorArm64()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Koin.core)
                implementation(Ktor.ktorCore)
                implementation(Ktor.ktorSerialization)
                implementation(Ktor.ktorContentNegotiation)
                implementation(Ktor.logging)
                implementation(Kermit.logging)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(Test.ktor)
                implementation(Test.koin)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Ktor.ktorAndroid)
                implementation(AndroidX.lifecycleViewModel)
                implementation(AndroidX.lifecycleRuntime)
            }
        }
        val androidTest by getting
        val iosMain by getting {
            dependencies {
                implementation(Ktor.ktoriOS)
            }
        }
        val iosTest by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }
}

android {
    compileSdk = Versions.targetsdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Versions.minsdk
        targetSdk = Versions.targetsdk
    }
}
