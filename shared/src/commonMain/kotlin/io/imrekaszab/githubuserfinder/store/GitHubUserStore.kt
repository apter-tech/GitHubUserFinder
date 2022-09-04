package io.imrekaszab.githubuserfinder.store

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import kotlinx.coroutines.flow.Flow

interface GitHubUserStore {
    fun getUsers(): Flow<List<GitHubUser>>
    fun getUserDetails(): Flow<GitHubUser>
    fun isFetchingFinished(): Flow<Boolean>
    fun getSavedUsers(): Flow<List<GitHubUser>>
}
