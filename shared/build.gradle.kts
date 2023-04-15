plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.mockmp)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.ksp)
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
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.bundles.commonMain)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.bundles.commonTest)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.lifecycle.runtime)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.sqldelight.android.driver)
                implementation(libs.ktor.android)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(libs.test.junitKtx)
                implementation(libs.sqldelight.sqlite.driver)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.ios)
                implementation(libs.sqldelight.native)
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
    compileSdk = libs.versions.targetSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(
            "VERSION_" + libs.versions.javaSourceCompatibility.get().replace(".", "_")
        )
        targetCompatibility = JavaVersion.valueOf(
            "VERSION_" + libs.versions.javaTargetCompatibility.get().replace(".", "_")
        )
    }
}

sqldelight {
    database("GitHubUserFinderDB") {
        packageName = "io.imrekaszab.githubuserfinder.db"
    }
}

mockmp {
    usesHelper = true
}
