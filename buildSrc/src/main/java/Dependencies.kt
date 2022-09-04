
object Versions {
    const val minsdk = 23
    const val targetsdk = 33
    const val gradle = "7.2.2"
    const val kotlin = "1.7.10"
    const val coroutines = "1.6.4"
    const val appCompat = "1.5.0"
    const val lifecycleViewModel = "2.5.1"
    const val sqldelight = "1.5.3"
    const val kmpNativeCoroutines = "0.12.6"

    const val compose = "1.3.0-beta01"
    const val composeActivity = "1.5.1"
    const val composeCompiler = "1.3.0"
    const val composeCoil = "2.2.0"
    const val composeNavigation = "2.5.1"

    const val koin = "3.2.0"
    const val ktor = "2.1.0"
    const val kermit = "1.1.3"
    const val slf4j = "2.0.0"
    const val ktlint = "11.0.0"
    const val junitKtx = "1.1.3"
}

object AndroidX {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleViewModel}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModel}"
}

object Compose {
    const val compiler = "androidx.compose.compiler:compiler:${Versions.composeCompiler}"
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.composeCoil}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    const val preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
}

object Koin {
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
}

object Ktor {
    const val ktorCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorSerialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val ktorContentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    const val ktorAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktoriOS = "io.ktor:ktor-client-ios:${Versions.ktor}"
    const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
}


object SQLDelight {
    const val androidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"
    const val native = "com.squareup.sqldelight:native-driver:${Versions.sqldelight}"
    const val coroutines = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqldelight}"
    const val nativeDriver = "com.squareup.sqldelight:sqlite-driver:${Versions.sqldelight}"
}

object Log {
    const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    const val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"
}

object Test {
    const val ktor = "io.ktor:ktor-client-mock:${Versions.ktor}"
    const val koin = "io.insert-koin:koin-test:${Versions.koin}"
    const val junitKtx = "androidx.test.ext:junit-ktx:${Versions.junitKtx}"
}
