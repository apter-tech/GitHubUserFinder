package io.imrekaszab.githubuserfinder.service

import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepository
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.CommonFlow
import io.imrekaszab.githubuserfinder.util.asCommonFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserService: GitHubUserAction, GitHubUserStore, KoinComponent {
    private val gitHubUserRepository: GitHubUserRepository by inject()

    @Throws(RuntimeException::class)
    override suspend fun searchUser(userName: String) {
        gitHubUserRepository.searchUser(userName)
    }

    override fun getUsers(): CommonFlow<List<GitHubUser>> =
        gitHubUserRepository.getUsers().asCommonFlow()
}
