package io.imrekaszab.githubuserfinder.di

import io.imrekaszab.githubuserfinder.api.GitHubApi
import io.imrekaszab.githubuserfinder.api.GitHubApiImpl
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepository
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLBuilder
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(baseUrl: String, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            networkModule(baseUrl),
            repositoryModule,
            dataModule,
            httpClientEngineModule,
            apiModule
        )
    }

// IOS
fun initKoin(baseUrl: String) = initKoin(baseUrl) {}

var apiModule = module {
    single<GitHubApi> { GitHubApiImpl(get()) }
}

var repositoryModule = module {
    single<GitHubUserRepository> { GitHubUserRepositoryImpl(get()) }
}

internal fun networkModule(baseUrl: String) = module {
    single {
        HttpClient(get()) {
            defaultRequest {
                url.takeFrom(
                    URLBuilder().takeFrom(baseUrl).apply {
                        encodedPath += "/${url.encodedPath}"
                    }
                )
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }
}
