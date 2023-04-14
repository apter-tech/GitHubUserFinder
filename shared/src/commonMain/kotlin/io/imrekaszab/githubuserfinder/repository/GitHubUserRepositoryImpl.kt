package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.database.DatabaseHelper
import io.imrekaszab.githubuserfinder.mapper.toData
import io.imrekaszab.githubuserfinder.mapper.toDomain
import io.imrekaszab.githubuserfinder.mapper.toDomains
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GitHubUserRepositoryImpl(private val databaseHelper: DatabaseHelper) : GitHubUserRepository {

    override suspend fun saveUser(user: GitHubUser) = databaseHelper.insertUser(user.toData())

    override suspend fun deleteUser(userId: Int) = databaseHelper.deleteById(userId.toLong())

    override suspend fun deleteAll() = databaseHelper.deleteAll()

    override fun getUserByUserName(userName: String): Flow<GitHubUser?> =
        databaseHelper.selectByUserName(userName)
            .map { it.firstOrNull()?.toDomain() }
            .flowOn(Dispatchers.Default)

    override fun getSavedUserList(): Flow<List<GitHubUser>> =
        databaseHelper.selectAllItems()
            .map { it.toDomains() }
            .flowOn(Dispatchers.Default)
}
