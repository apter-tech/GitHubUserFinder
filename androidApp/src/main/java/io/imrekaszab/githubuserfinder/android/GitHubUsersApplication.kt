package io.imrekaszab.githubuserfinder.android

import android.app.Application
import android.content.Context
import android.util.Log
import io.imrekaszab.githubuserfinder.di.initKoin
import org.koin.dsl.module

class GitHubUsersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val baseUrl = BuildConfig.BASE_PATH
        initKoin(baseUrl,
            module {
                single<Context> { this@GitHubUsersApplication }
                single {
                    { Log.i("Startup", "App started on Android!") }
                }
            }
        )
    }
}
