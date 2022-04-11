package io.imrekaszab.githubuserfinder.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.ios.Ios
import org.koin.core.module.Module
import org.koin.dsl.module

actual var httpClientEngineModule: Module = module {
    single<HttpClientEngine> { Ios.create() }
}
