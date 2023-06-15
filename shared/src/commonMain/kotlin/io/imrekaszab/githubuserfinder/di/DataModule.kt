package io.imrekaszab.githubuserfinder.di

import io.imrekaszab.githubuserfinder.service.GitHubUserService
import io.imrekaszab.githubuserfinder.service.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.service.store.GitHubUserStore
import org.koin.dsl.binds
import org.koin.dsl.module

var dataModule = module {
    single { GitHubUserService(get()) } binds arrayOf(GitHubUserAction::class, GitHubUserStore::class)
}
