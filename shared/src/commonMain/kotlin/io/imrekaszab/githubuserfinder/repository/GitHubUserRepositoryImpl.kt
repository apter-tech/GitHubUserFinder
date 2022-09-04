package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.database.DatabaseHelper
import io.imrekaszab.githubuserfinder.mapper.toData
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserRepositoryImpl : GitHubUserRepository, KoinComponent {

    private val databaseHelper: DatabaseHelper by inject()

    override suspend fun saveUser(user: GitHubUser) {
        databaseHelper.insertUser(user.toData())
    }

    override suspend fun deleteUser(userId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getUserList(): Flow<List<GitHubUser>> {
        TODO("Not yet implemented")
    }
}
