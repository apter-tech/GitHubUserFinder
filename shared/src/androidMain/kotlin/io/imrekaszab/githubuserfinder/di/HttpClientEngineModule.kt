package io.imrekaszab.githubuserfinder.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.core.module.Module
import org.koin.dsl.module

actual var httpClientEngineModule: Module = module {
    single<HttpClientEngine> { Android.create() }
}
