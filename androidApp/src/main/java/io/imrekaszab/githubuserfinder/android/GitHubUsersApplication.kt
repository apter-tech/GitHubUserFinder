package io.imrekaszab.githubuserfinder.android

import android.app.Application
import io.imrekaszab.githubuserfinder.android.di.appModule
import io.imrekaszab.githubuserfinder.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level


class GitHubUsersApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val baseUrl = BuildConfig.BASE_PATH
        initKoin(baseUrl) {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@GitHubUsersApplication)
            modules(appModule)
        }
    }
}
