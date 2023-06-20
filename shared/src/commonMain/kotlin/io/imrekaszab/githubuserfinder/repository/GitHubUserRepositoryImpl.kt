package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.api.GitHubApi
import io.imrekaszab.githubuserfinder.database.DatabaseHelper
import io.imrekaszab.githubuserfinder.mapper.toData
import io.imrekaszab.githubuserfinder.mapper.toDomain
import io.imrekaszab.githubuserfinder.mapper.toDomainModels
import io.imrekaszab.githubuserfinder.mapper.toDomains
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GitHubUserRepositoryImpl(
    private val databaseHelper: DatabaseHelper,
    private val gitHubApi: GitHubApi
) : GitHubUserRepository {
    override suspend fun searchUser(userName: String, page: Int, offset: Int): Pair<Int, List<GitHubUser>> {
        val result = gitHubApi.searchUser(userName, page, offset)
        return result.total_count to result.items.toDomainModels().toMutableList()
    }

    override suspend fun refreshUserDetails(userName: String): GitHubUser =
        getUserByUserName(userName).firstOrNull() ?: gitHubApi.refreshUserDetails(userName).toDomain()

    override suspend fun saveUser(user: GitHubUser) = databaseHelper.insertUser(user.toData())

    override suspend fun deleteUser(userId: Int) = databaseHelper.deleteById(userId.toLong())

    override suspend fun deleteAllUsers() = databaseHelper.deleteAll()

    override fun getUserByUserName(userName: String): Flow<GitHubUser?> =
        databaseHelper.selectByUserName(userName)
            .map { it.firstOrNull()?.toDomain() }
            .flowOn(Dispatchers.Default)

    override fun getSavedUserList(): Flow<List<GitHubUser>> =
        databaseHelper.selectAllItems()
            .map { it.toDomains() }
            .flowOn(Dispatchers.Default)
}
