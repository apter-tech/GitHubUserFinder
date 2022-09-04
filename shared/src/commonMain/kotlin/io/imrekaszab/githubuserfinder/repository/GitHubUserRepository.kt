package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import kotlinx.coroutines.flow.Flow

interface GitHubUserRepository {
    suspend fun saveUser(user: GitHubUser)
    suspend fun deleteUser(userId: Int)
    suspend fun deleteAll()
    fun getUserById(userId: Int): Flow<GitHubUser>
    fun getSavedUserList(): Flow<List<GitHubUser>>
}
