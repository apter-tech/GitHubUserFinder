package io.imrekaszab.githubuserfinder.service

import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.model.domain.GitHubUserDetails
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepository
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.util.ApplicationDispatcher
import io.imrekaszab.githubuserfinder.util.CommonFlow
import io.imrekaszab.githubuserfinder.util.asCommonFlow
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GitHubUserService : GitHubUserAction, GitHubUserStore, KoinComponent {
    private val gitHubUserRepository: GitHubUserRepository by inject()

    @Throws(RuntimeException::class)
    override suspend fun searchUser(userName: String) = withContext(ApplicationDispatcher) {
        gitHubUserRepository.searchUser(userName)
    }

    @Throws(RuntimeException::class)
    override suspend fun refreshUserDetails(userName: String) =
        withContext(ApplicationDispatcher) {
            gitHubUserRepository.refreshUserDetails(userName)
        }

    override fun getUsers(): CommonFlow<List<GitHubUser>> =
        gitHubUserRepository.getUsers().asCommonFlow()

    override fun getUserDetails(): CommonFlow<GitHubUserDetails> =
        gitHubUserRepository.getUserDetails().asCommonFlow()
}
