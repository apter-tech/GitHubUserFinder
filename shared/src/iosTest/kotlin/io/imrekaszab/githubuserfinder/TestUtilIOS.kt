package io.imrekaszab.githubuserfinder

import co.touchlab.sqliter.DatabaseConfiguration
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection
import io.imrekaszab.githubuserfinder.db.GitHubUserFinderDB

internal actual fun testDbConnection(): SqlDriver {
    val schema = GitHubUserFinderDB.Schema
    return NativeSqliteDriver(
        DatabaseConfiguration(
            name = "GitHubUserFinderDB",
            version = schema.version,
            create = { connection ->
                wrapConnection(connection) { schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { schema.migrate(it, oldVersion, newVersion) }
            },
            inMemory = true
        )
    )
}
