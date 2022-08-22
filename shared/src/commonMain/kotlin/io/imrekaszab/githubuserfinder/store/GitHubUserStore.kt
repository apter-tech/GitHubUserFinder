package io.imrekaszab.githubuserfinder.store

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import kotlinx.coroutines.flow.Flow

interface GitHubUserStore {
    fun getUsers(): Flow<List<GitHubUser>>
    fun getUserDetails(): Flow<GitHubUserDetails>
    fun isFetchingFinished(): Flow<Boolean>
}
