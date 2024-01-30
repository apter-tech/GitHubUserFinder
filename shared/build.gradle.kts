plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.mockmp)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.ksp)
    alias(libs.plugins.moko.resources)
}

val appJvmTarget: JavaVersion by rootProject.extra

kotlin {
    jvmToolchain(appJvmTarget.majorVersion.toInt())

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "$appJvmTarget"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            export(libs.moko.resources)
            export(libs.moko.graphics)
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.commonMain)
            api(libs.moko.resources)
        }
        commonTest.dependencies {
            implementation(kotlin("test-junit"))
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
            implementation(libs.bundles.commonTest)
        }
        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.runtime)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.ktor.android)
            api(libs.moko.compose)
        }
        androidUnitTest.dependencies {
            implementation(libs.test.junitKtx)
            implementation(libs.sqldelight.sqlite.driver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.ios)
            implementation(libs.sqldelight.native)
        }
    }
}

android {
    namespace = "io.imrekaszab.githubuserfinder"
    compileSdk = libs.versions.targetSdk.get().toInt()
    sourceSets["main"].res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
    sourceSets["main"].java.srcDir(File(buildDir, "generated/moko/androidMain/src"))
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

sqldelight {
    database("GitHubUserFinderDB") {
        packageName = "io.imrekaszab.githubuserfinder.db"
    }
}

mockmp {
    usesHelper = true
    installWorkaround()
}

multiplatformResources {
    multiplatformResourcesPackage = "io.imrekaszab.githubuserfinder"
}

val org.jetbrains.kotlin.konan.target.KonanTarget.enabledOnCurrentHost
    get() = org.jetbrains.kotlin.konan.target.HostManager().isEnabled(this)

extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
    targets.matching { target ->
        target is org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget &&
            !target.konanTarget.enabledOnCurrentHost
    }
        .forEach { target ->
            tasks.findByName("${target.name}ProcessResources")?.enabled = false
        }
}
