package io.imrekaszab.githubuserfinder.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.imrekaszab.githubuserfinder.db.GitHubUserDataModel
import io.imrekaszab.githubuserfinder.db.GitHubUserFinderDB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class DatabaseHelper(
    sqlDriver: SqlDriver,
    private val backgroundDispatcher: CoroutineDispatcher
) : KoinComponent {
    private val database: GitHubUserFinderDB = GitHubUserFinderDB(sqlDriver)

    fun selectAllItems(): Flow<List<GitHubUserDataModel>> =
        database.gitHubUserDataModelQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    suspend fun insertUser(user: GitHubUserDataModel) =
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

    fun selectByUserName(userName: String): Flow<List<GitHubUserDataModel>> =
        database.gitHubUserDataModelQueries
            .selectByUserName(userName)
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    suspend fun deleteById(id: Long) =
        database.transactionWithContext(backgroundDispatcher) {
            database.gitHubUserDataModelQueries.deleteById(id)
        }

    suspend fun deleteAll() =
        database.transactionWithContext(backgroundDispatcher) {
            database.gitHubUserDataModelQueries.deleteAll()
        }
}
