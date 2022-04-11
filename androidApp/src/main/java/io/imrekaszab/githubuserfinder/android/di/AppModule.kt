package io.imrekaszab.githubuserfinder.android.di

import io.imrekaszab.githubuserfinder.android.viewmodel.GitHubUserDetailsViewModel
import io.imrekaszab.githubuserfinder.android.viewmodel.GitHubUserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { GitHubUserListViewModel(get(), get()) }
    viewModel { GitHubUserDetailsViewModel(get(), get()) }
}
