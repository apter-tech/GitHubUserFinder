package io.imrekaszab.githubuserfinder

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.imrekaszab.githubuserfinder.db.GitHubUserFinderDB

internal actual fun testDbConnection(): SqlDriver {
    // Try to use the android driver (which only works if we're on robolectric).
    // Fall back to jdbc if that fails.
    return try {
        val app = ApplicationProvider.getApplicationContext<Application>()
        AndroidSqliteDriver(
            GitHubUserFinderDB.Schema,
            app,
            "GitHubUserFinderDB"
        )
    } catch (@Suppress("SwallowedException") exception: IllegalStateException) {
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            .also { GitHubUserFinderDB.Schema.create(it) }
    }
}
