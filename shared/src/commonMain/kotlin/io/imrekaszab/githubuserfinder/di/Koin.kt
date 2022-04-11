package io.imrekaszab.githubuserfinder.di

import io.imrekaszab.githubuserfinder.api.GitHubApi
import io.imrekaszab.githubuserfinder.api.GitHubApiImpl
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepository
import io.imrekaszab.githubuserfinder.repository.GitHubUserRepositoryImpl
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
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
                url.takeFrom(URLBuilder().takeFrom(baseUrl).apply {
                    encodedPath += "/${url.encodedPath}"
                })
            }

            install(JsonFeature) {
                val json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                }
                serializer = KotlinxSerializer(json)
            }
            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }
}

