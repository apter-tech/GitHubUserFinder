package io.imrekaszab.githubuserfinder.di

import io.imrekaszab.githubuserfinder.repository.GitHubUserRepository
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepositoryImpl
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            repositoryModule,
            dataModule
        )
    }

// IOS
fun initKoin() = initKoin {}

var repositoryModule = module {
    single<GitHubUserRepository> { GitHubUserRepositoryImpl() }
}
