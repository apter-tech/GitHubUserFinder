package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.database.DatabaseHelper
import io.imrekaszab.githubuserfinder.mapper.toData
import io.imrekaszab.githubuserfinder.mapper.toDomain
import io.imrekaszab.githubuserfinder.mapper.toDomains
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserRepositoryImpl : GitHubUserRepository, KoinComponent {

    private val databaseHelper: DatabaseHelper by inject()

    override suspend fun saveUser(user: GitHubUser) {
        databaseHelper.insertUser(user.toData())
    }

    override suspend fun deleteUser(userId: Int) {
        databaseHelper.deleteById(userId.toLong())
    }

    override suspend fun deleteAll() {
        databaseHelper.deleteAll()
    }

    override fun getUserById(userId: Int): Flow<GitHubUser> =
        databaseHelper.selectById(userId.toLong())
            .map { it.first().toDomain() }

    override fun getSavedUserList(): Flow<List<GitHubUser>> =
        databaseHelper.selectAllItems()
            .map { it.toDomains() }
}
