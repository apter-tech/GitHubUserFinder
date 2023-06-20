# GitHubUserFinderKMM
![GitHub Actions build status](https://github.com/kaszabimre/GitHubUserFinderKMM/actions/workflows/GitHubUserFinderKMM.yml/badge.svg)

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
- Shared resources (svg, strings) with moko-resources
- MVVM + Reducer implementation = MVI
- List and detail screens
- Favourite user feature with SQLDelight
- Simple paging functionality
- Linter & formatter (detekt, swiftlint)
- Common tests
- Dark mode
- Automated dependency update with Renovate
- GitHub Actions config:  [GitHubUserFinderKMM.yml](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/.github/workflows/GitHubUserFinderKMM.yml)
- [GitHub API](https://docs.github.com/en/rest/search#search-users)
- Coverage report (kover)

### Libraries
> Check [Dependencies.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/buildSrc/src/main/java/Dependencies.kt) for more details

- 🌎 [Ktor](https://github.com/ktorio/ktor) - Network
  [![GitHub Repo stars](https://img.shields.io/github/stars/ktorio/ktor)](https://github.com/ktorio/ktor)
- 🔒 [SQLDelight](https://github.com/cashapp/sqldelight) - LocalDB
  [![GitHub Repo stars](https://img.shields.io/github/stars/cashapp/sqldelight)](https://github.com/cashapp/sqldelight)
- 💉 [Koin](https://github.com/InsertKoinIO/koin) - DI framework
  [![GitHub Repo stars](https://img.shields.io/github/stars/InsertKoinIO/koin)](https://github.com/InsertKoinIO/koin)
- 📋 [Kermit](https://github.com/touchlab/Kermit) - Logger
  [![GitHub Repo stars](https://img.shields.io/github/stars/touchlab/Kermit)](https://github.com/touchlab/Kermit)
- 🎨 [moko resources](https://github.com/icerockdev/moko-resources) - Shared resources
  [![GitHub Repo stars](https://img.shields.io/github/stars/icerockdev/moko-resources)](https://github.com/icerockdev/moko-resources)
- 🚦 Testing - Common unit tests in `shared` module with [MockHttpClient](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/shared/src/commonTest/kotlin/io/imrekaszab/githubuserfinder/MockHttpClient.kt)
- 🔍 Linter & formatter
    - [Detekt](https://github.com/detekt/detekt) - `shared + Android`
      ![GitHub Repo stars](https://img.shields.io/github/stars/detekt/Detekt)
       ```
       ./gradlew detekt
       ```
    - [Swiftlint](https://github.com/realm/SwiftLint) - `iOS`
      ![GitHub Repo stars](https://img.shields.io/github/stars/realm/SwiftLint)
        - [Rules in swiftlint.yaml](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/iosApp/.swiftlint.yml)
       ```
       swiftlint --fix
       ```

### Code coverage

- 📋 [Kover](https://github.com/Kotlin/kotlinx-kover) - Kotlin code coverage tool
  [![GitHub Repo stars](https://img.shields.io/github/stars/Kotlin/kotlinx-kover)](https://github.com/Kotlin/kotlinx-kover)

> Use `./gradlew koverMergedVerify koverMergedReport` to verify and generate the coverage report with a custom rule:

```Kotlin
kover {
        verify {
            rule {
                isEnabled = true
                name = "Minimum coverage verification error"
                target =
                    kotlinx.kover.api.VerificationTarget.ALL

                bound {
                    minValue = 90
                    maxValue = 100
                    counter =
                        kotlinx.kover.api.CounterType.LINE
                    valueType =
                        kotlinx.kover.api.VerificationValueType.COVERED_PERCENTAGE
                }
            }
        }
    }
```

> After that we can use the [Kotlinx Kover Report](https://github.com/marketplace/actions/kotlinx-kover-report) to add the coverage report to the PR as a comment


### IDEs

- Android Studio Flamingo | 2022.2.1 | with [KMM plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
- Xcode 14.3
