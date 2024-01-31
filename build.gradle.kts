plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.moko.resources) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.kover)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

ext {
    set("appJvmTarget", JavaVersion.VERSION_1_8)
}

dependencies {
    kover(project(":androidApp"))
    kover(project(":shared"))
}

allprojects {
    apply(plugin = rootProject.libs.plugins.kover.get().pluginId)
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)
    detekt {
        source.setFrom(
            objects.fileCollection().from(
                io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_SRC_DIR_JAVA,
                io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_TEST_SRC_DIR_JAVA,
                io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_SRC_DIR_KOTLIN,
                io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_TEST_SRC_DIR_KOTLIN,
                "src/androidMain",
                "src/commonMain",
                "src/iosMain",
                "src/commonTest",
                "src/iosTest",
                "src/androidUnitTest",
                "src/test",
                "src/testDebug",
                "src/testRelease",
                "build.gradle.kts",
            )
        )
        buildUponDefaultConfig = false
        // point to your custom config defining rules to run, overwriting default behavior
        config.setFrom(files("$rootDir/config/detekt.yml"))
    }
    dependencies {
        detektPlugins(rootProject.libs.detekt.formatting)
    }

    koverReport {
        defaults {
            plugins.withId("com.android.library") {
                mergeWith("release")
            }
            plugins.withId("com.android.application") {
                mergeWith("release")
            }
        }
        filters {
            excludes {
                classes(
                    "*.MR*",
                    "*.BuildConfig",
                    "*.Mock*",
                    "*.TestUtilAndroidKt",
                    "io.imrekaszab.githubuserfinder.db.*",
                    "*.di.*",
                )
            }
        }
    }
}
