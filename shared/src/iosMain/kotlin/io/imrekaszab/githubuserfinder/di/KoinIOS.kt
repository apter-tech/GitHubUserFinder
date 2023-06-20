package io.imrekaszab.githubuserfinder.di

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.imrekaszab.githubuserfinder.db.GitHubUserFinderDB
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.ObjCProtocol
import kotlinx.cinterop.getOriginalKotlinClass
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
fun Koin.loggerWithTag(tag: String) = get<Logger>(qualifier = null) { parametersOf(tag) }

fun Koin.get(objCClass: ObjCClass): Any? {
    val kClazz = getOriginalKotlinClass(objCClass) ?: return null
    return get(kClazz)
}

fun Koin.get(objCProtocol: ObjCProtocol): Any? {
    val kClazz = getOriginalKotlinClass(objCProtocol) ?: return null
    return get(kClazz)
}
