plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin
    id("com.rickclephas.kmp.nativecoroutines") version Versions.kmpNativeCoroutines
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.squareup.sqldelight")
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
                implementation(SQLDelight.coroutines)
                implementation(Log.kermit)
                implementation(Log.slf4j)
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
                implementation(SQLDelight.androidDriver)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Test.junitKtx)
                implementation(SQLDelight.nativeDriver)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(SQLDelight.native)
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

sqldelight {
    database("GitHubUserFinderDB") {
        packageName = "io.imrekaszab.githubuserfinder.db"
    }
}
