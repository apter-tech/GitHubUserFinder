package io.imrekaszab.githubuserfinder.di

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.imrekaszab.githubuserfinder.db.GitHubUserFinderDB
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            GitHubUserFinderDB.Schema,
            get(),
            "GitHubUserFinderDB"
        )
    }
}
