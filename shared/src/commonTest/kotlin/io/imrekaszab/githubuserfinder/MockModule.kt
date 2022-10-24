package io.imrekaszab.githubuserfinder

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import org.koin.dsl.module

internal val mockModule = module {
    single {
        mockHttpClient
    }
    single {
        testDbConnection()
    }
    single {
        Logger(StaticConfig())
    }
}
