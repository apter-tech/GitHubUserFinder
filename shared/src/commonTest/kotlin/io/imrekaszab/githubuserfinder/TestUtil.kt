package io.imrekaszab.githubuserfinder

import com.squareup.sqldelight.db.SqlDriver

internal expect fun testDbConnection(): SqlDriver
