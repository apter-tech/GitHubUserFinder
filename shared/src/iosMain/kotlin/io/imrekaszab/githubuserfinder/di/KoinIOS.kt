package io.imrekaszab.githubuserfinder.di

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.imrekaszab.githubuserfinder.db.GitHubUserFinderDB
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

fun initKoinIos(
    baseUrl: String,
    doOnStartup: () -> Unit
): KoinApplication = initKoin(
    baseUrl,
    module {
        single { doOnStartup }
    }
)

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(GitHubUserFinderDB.Schema, "GitHubUserFinderDB") }
}

// Access from Swift to create a logger
@Suppress("unused")
fun Koin.loggerWithTag(tag: String) =
    get<Logger>(qualifier = null) { parametersOf(tag) }
