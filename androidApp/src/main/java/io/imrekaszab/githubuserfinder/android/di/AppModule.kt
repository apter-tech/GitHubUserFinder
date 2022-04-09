package io.imrekaszab.githubuserfinder.android.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { GitHubUserListViewModel(get(), get()) }
}
