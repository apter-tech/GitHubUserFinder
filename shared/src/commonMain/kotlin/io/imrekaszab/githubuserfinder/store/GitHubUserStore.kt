package io.imrekaszab.githubuserfinder.store

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import io.imrekaszab.githubuserfinder.util.CommonFlow

interface GitHubUserStore {
    fun getUsers(): CommonFlow<List<GitHubUser>>
    fun getUserDetails(): CommonFlow<GitHubUserDetails>
}
