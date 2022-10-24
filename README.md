# GitHubUserFinderKMM
![GitHub Actions build status](https://github.com/kaszabimre/GitHubUserFinderKMM/actions/workflows/GitHubUserFinderKMM-Android.yml/badge.svg)
![GitHub Actions build status](https://github.com/kaszabimre/GitHubUserFinderKMM/actions/workflows/GitHubUserFinderKMM-iOS.yml/badge.svg)

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

- Shared ViewModels :rocket:
- MVVM + Reducer implementation = MVI
- List and detail screens
- Favourite user feature with SQLDelight
- Simple paging functionality
- Linter & formatter (ktlint, swiftlint)
- Dark mode
- Error handling
- Common tests
- GitHub Actions config:  [GitHubUserFinderKMM-Android.yml](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/.github/workflows/GitHubUserFinderKMM-Android.yml) + [GitHubUserFinderKMM-iOS.yml](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/.github/workflows/GitHubUserFinderKMM-iOS.yml)
- [GitHub API](https://docs.github.com/en/rest/search#search-users)

### Libraries
> Check [Dependencies.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/buildSrc/src/main/java/Dependencies.kt) for more details

- :boom: [KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines) - Native coroutines and flows
  [![GitHub Repo stars](https://img.shields.io/github/stars/rickclephas/KMP-NativeCoroutines)](https://github.com/rickclephas/KMP-NativeCoroutines)
  [![latest release](https://img.shields.io/github/v/release/rickclephas/KMP-NativeCoroutines?label=latest%20release&sort=semver)](https://github.com/rickclephas/KMP-NativeCoroutines/releases)
- 🌎 [Ktor](https://github.com/ktorio/ktor) - Network
  [![GitHub Repo stars](https://img.shields.io/github/stars/ktorio/ktor)](https://github.com/ktorio/ktor)
  [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.ktor/ktor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.ktor)
- 🔒 [SQLDelight](https://github.com/cashapp/sqldelight) - LocalDB
  [![GitHub Repo stars](https://img.shields.io/github/stars/cashapp/sqldelight)](https://github.com/cashapp/sqldelight)
- 💉 [Koin](https://github.com/InsertKoinIO/koin) - DI framework
  [![GitHub Repo stars](https://img.shields.io/github/stars/InsertKoinIO/koin)](https://github.com/InsertKoinIO/koin)
  [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.insert-koin/koin-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.insert-koin/koin-core)
- 📋 [Kermit](https://github.com/touchlab/Kermit) - Logger
  [![GitHub Repo stars](https://img.shields.io/github/stars/touchlab/Kermit)](https://github.com/touchlab/Kermit)
  [![Maven Central](https://img.shields.io/maven-central/v/co.touchlab/kermit.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22co.touchlab%22%20AND%20a:%22kermit%22)
- 🎨 [Coil](https://coil-kt.github.io/coil/) - Image loader `Android`
  [![GitHub Repo stars](https://img.shields.io/github/stars/coil-kt/coil)](https://github.com/coil-kt/coil)
  [![Maven Central](https://img.shields.io/maven-central/v/io.coil-kt/coil-compose.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.coil-kt%22%20AND%20a:%22coil-compose%22)
- 🚦 Testing - Common unit tests in `shared` module with [MockHttpClient](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/shared/src/commonTest/kotlin/io/imrekaszab/githubuserfinder/MockHttpClient.kt)
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
       swiftlint --fix
       ```

### IDEs

- Android Studio Dolphin | 2021.3.1 | with [KMM plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
- Xcode 14.0.1

## Dependency versions

This project is using a [gradle-versions-plugin](https://github.com/ben-manes/gradle-versions-plugin) to manage dependency versions.

### Checking for new versions

To check if dependencies are in need of updating:

```sh
./gradlew dependencyUpdates
```

### What's next?

- UI tests
- Fancy animations

