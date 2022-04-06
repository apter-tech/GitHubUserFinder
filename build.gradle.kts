buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath(Kotlin.Serialization.plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
    }
}
