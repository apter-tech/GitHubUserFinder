# GitHubUserFinderKMM
Kotlin Multiplatform Mobile sample project with Jetpack Compose and SwiftUI

Check this basic, simple KMM project with latest and greatest libraries. You can also easily learn and keep your knowledge up-to-date in both [Jeptack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjw7vuUBhBUEiwAEdu2pHTM59Y0NTVLcoFuOJHq5g8p3dJludRLuITkxy54fKMp-3YafHSjNRoCSIwQAvD_BwE&gclsrc=aw.ds) and [SwiftUI](https://developer.apple.com/xcode/swiftui/).

![iOS](https://img.shields.io/badge/iOS-000000?style=for-the-badge&logo=ios&logoColor=white)
![Swift](https://img.shields.io/badge/Swift-FA7343?style=for-the-badge&logo=swift&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

Android | iOS
:--: | :--:
<img src="/screenshots/android.gif" width="300" /> | <img src="/screenshots/ios.gif" width="300" />


### About the project

- List and detail screens 
- Simple paging functionality
- MVVM 
- Linter & formatter (ktlint, swiftlint)
- Dark mode
- Error handling
- [GitHub API](https://docs.github.com/en/rest/search#search-users) 
- [CommonFlow](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/util/CommonFlow.kt)
> In this project the flow is running on the main thread and then invoking “main-safe” suspend functions provided by Ktor.
> https://johnoreilly.dev/posts/kotlinmultiplatform-swift-combine_publisher-flow/
 ```kotlin
fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)

class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun subscribe(block: (T) -> Unit): Closeable {
        val job = Job()
        onEach { block(it) }.launchIn(CoroutineScope(job + Dispatchers.Main))
        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}
```
- with custom `ApplicationDispatcher`
   - [Android](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/shared/src/androidMain/kotlin/io/imrekaszab/githubuserfinder/util/Dispatcher.kt)
   ```kotlin
   internal actual val ApplicationDispatcher: CoroutineContext = Dispatchers.Default
   ```
   - [iOS](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/shared/src/iosMain/kotlin/io/imrekaszab/githubuserfinder/util/Dispatcher.kt)
   ```kotlin
   internal actual val ApplicationDispatcher: CoroutineContext =
    NsQueueDispatcher(dispatch_get_main_queue())

   internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
   ) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
      }
   }
   ```

### Libraries
> Check [Dependencies.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/buildSrc/src/main/java/Dependencies.kt) for more details

- 🌎 [Ktor](https://github.com/ktorio/ktor) - Network
[![GitHub Repo stars](https://img.shields.io/github/stars/ktorio/ktor)](https://github.com/ktorio/ktor)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.ktor/ktor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.ktor)
- 💉 [Koin](https://github.com/InsertKoinIO/koin) - DI framework
[![GitHub Repo stars](https://img.shields.io/github/stars/InsertKoinIO/koin)](https://github.com/InsertKoinIO/koin)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.insert-koin/koin-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.insert-koin/koin-core)
- 📋 [Kermit](https://github.com/touchlab/Kermit) - Logger
[![GitHub Repo stars](https://img.shields.io/github/stars/touchlab/Kermit)](https://github.com/touchlab/Kermit)
[![Maven Central](https://img.shields.io/maven-central/v/co.touchlab/kermit.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22co.touchlab%22%20AND%20a:%22kermit%22)
- 🎨 [Coil](https://coil-kt.github.io/coil/) - Image loader `Android`
[![GitHub Repo stars](https://img.shields.io/github/stars/coil-kt/coil)](https://github.com/coil-kt/coil)
[![Maven Central](https://img.shields.io/maven-central/v/io.coil-kt/coil-compose.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.coil-kt%22%20AND%20a:%22coil-compose%22)
- 🔍 Linter & formatter
   - [Ktlint](https://github.com/JLLeitschuh/ktlint-gradle) - `shared + Android` 
![GitHub Repo stars](https://img.shields.io/github/stars/JLLeitschuh/ktlint-gradle)
      ```
      ./gradlew ktlintCheck ktlintFormat
      ```
   - [Swiftlint](https://github.com/realm/SwiftLint) - `iOS`
![GitHub Repo stars](https://img.shields.io/github/stars/realm/SwiftLint)
      - [Rules in swiftlint.yaml](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/iosApp/.swiftlint.yml)
      ```
      swiftlint autocorrect
      ```

### IDEs

- Android Studio Chipmunk | 2021.2.1 Patch 1 | with [KMM plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
- Xcode 13.2.1

### What's next?

- Testing (UI and integration tests)
- Combine and AsyncStream extensions
- Fancy animations
- Shared ViewModels??? We’ll see 😜

### KMM beta roadmap
- https://blog.jetbrains.com/kotlin/2022/05/kotlin-multiplatform-mobile-beta-roadmap-update/
