package io.imrekaszab.githubuserfinder.repository

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import kotlinx.coroutines.flow.Flow

interface GitHubUserRepository {
    suspend fun searchUser(userName: String)
    fun getUsers(): Flow<List<GitHubUser>>
}
