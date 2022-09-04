package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import kotlinx.coroutines.flow.Flow

class GitHubUserRepositoryImpl : GitHubUserRepository {

    override suspend fun saveUser(user: GitHubUser) {
        TODO("Not yet implemented")
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
