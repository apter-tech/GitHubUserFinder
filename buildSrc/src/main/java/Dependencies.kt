
object Versions {
    const val minsdk = 23
    const val targetsdk = 31
    const val kotlin = "1.6.10"
    const val appCompat = "1.4.1"

    const val compose = "1.1.0"
    const val composeActivity = "1.4.0"
    const val composeCompiler = "1.1.0"
}

object AndroidX {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
}

object Compose {
    const val compiler = "androidx.compose.compiler:compiler:${Versions.composeCompiler}"
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
}
