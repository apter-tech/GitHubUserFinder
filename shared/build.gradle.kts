plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.kotlin
    id("com.android.library")
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    ios()
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
        all {
            languageSettings.apply {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("com.russhwolf.settings.ExperimentalSettingsApi")
            }
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
                implementation(Test.coroutines)
                implementation(Test.turbine)
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
    namespace = "io.imrekaszab.githubuserfinder"
    compileSdk = Versions.targetsdk
    defaultConfig {
        minSdk = Versions.minsdk
        targetSdk = Versions.targetsdk
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        unitTests.all {
            it.extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
                isDisabled.set(it.name == "testDebugUnitTest")
            }
        }
    }

    kover {
        instrumentation {
            excludeTasks.add("testDebugUnitTest")
        }
    }
}

sqldelight {
    database("GitHubUserFinderDB") {
        packageName = "io.imrekaszab.githubuserfinder.db"
    }
}
