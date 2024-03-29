package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import kotlinx.coroutines.flow.Flow

interface GitHubUserRepository {
    suspend fun searchUser(userName: String, page: Int, offset: Int): Pair<Int, List<GitHubUser>>
    suspend fun refreshUserDetails(userName: String): GitHubUser
    suspend fun saveUser(user: GitHubUser)
    suspend fun deleteUser(userId: Int)
    suspend fun deleteAllUsers()
    fun getUserByUserName(userName: String): Flow<GitHubUser?>
    fun getSavedUserList(): Flow<List<GitHubUser>>
}
