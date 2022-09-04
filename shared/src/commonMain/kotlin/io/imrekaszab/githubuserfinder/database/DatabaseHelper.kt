package io.imrekaszab.githubuserfinder.database

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.imrekaszab.githubuserfinder.db.GitHubUserDataModel
import io.imrekaszab.githubuserfinder.db.GitHubUserFinderDB
import io.imrekaszab.githubuserfinder.di.injectLogger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class DatabaseHelper(
    sqlDriver: SqlDriver,
    private val backgroundDispatcher: CoroutineDispatcher
): KoinComponent {
    private val log: Logger by injectLogger("DatabaseHelper")
    private val database: GitHubUserFinderDB = GitHubUserFinderDB(sqlDriver)

    fun selectAllItems(): Flow<List<GitHubUserDataModel>> =
        database.gitHubUserDataModelQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    suspend fun insertUser(user: GitHubUserDataModel) {
        log.i("insertUser: $user")
        database.transactionWithContext(backgroundDispatcher) {
            database.gitHubUserDataModelQueries.insertUser(
                user.id,
                user.login,
                user.avatarUrl,
                user.score,
                user.name,
                user.company,
                user.blog,
                user.location,
                user.email,
                user.bio,
                user.twitterUsername,
                user.followers,
                user.following,
                user.publicRepos
            )
        }
    }

    fun selectById(id: Int): Flow<List<GitHubUserDataModel>> =
        database.gitHubUserDataModelQueries
            .selectById(id.toLong())
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    suspend fun deleteAll() {
        log.i { "Database Cleared" }
        database.transactionWithContext(backgroundDispatcher) {
            database.gitHubUserDataModelQueries.deleteAll()
        }
    }
}
