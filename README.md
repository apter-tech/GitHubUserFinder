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
- Linter & formatter (detekt, swiftlint)
- Dark mode
- Error handling
- Common tests
- GitHub Actions config:  [GitHubUserFinderKMM-Android.yml](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/.github/workflows/GitHubUserFinderKMM-Android.yml) + [GitHubUserFinderKMM-iOS.yml](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/main/.github/workflows/GitHubUserFinderKMM-iOS.yml)
- [GitHub API](https://docs.github.com/en/rest/search#search-users)
- Coverage report (kover)

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
  [![Maven Central](https://img.shields.io/maven-central/v/org.jetbrains.kotlinx.kover/org.jetbrains.kotlinx.kover.gradle.plugin.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22org.jetbrains.kotlinx.kover%22%20AND%20a:%22org.jetbrains.kotlinx.kover.gradle.plugin%22)
  
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
                    minValue = 80
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

|File|Coverage [84.31%]|
|:-|:-:|
|[shared/src/androidMain/kotlin/io/imrekaszab/githubuserfinder/di/KoinAndroid.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FandroidMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fdi%2FKoinAndroid.kt)|33.33%|
|[shared/src/androidTest/kotlin/io/imrekaszab/githubuserfinder/TestUtilAndroid.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FandroidTest%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2FTestUtilAndroid.kt)|55.56%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/database/CoroutinesExtensions.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fdatabase%2FCoroutinesExtensions.kt)|100.00%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/database/DatabaseHelper.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fdatabase%2FDatabaseHelper.kt)|100.00%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/di/Koin.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fdi%2FKoin.kt)|39.58%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/mapper/Mappers.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fmapper%2FMappers.kt)|100.00%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/model/domain/GitHubUser.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fmodel%2Fdomain%2FGitHubUser.kt)|100.00%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/repository/GitHubUserRepositoryImpl.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Frepository%2FGitHubUserRepositoryImpl.kt)|100.00%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/service/GitHubUserService.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fservice%2FGitHubUserService.kt)|66.67%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/util/mvi/Reducer.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Futil%2Fmvi%2FReducer.kt)|92.31%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/util/mvi/TimeCapsule.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Futil%2Fmvi%2FTimeCapsule.kt)|66.67%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/viewmodel/details/GitHubUserDetailsModel.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fviewmodel%2Fdetails%2FGitHubUserDetailsModel.kt)|91.67%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/viewmodel/details/GitHubUserDetailsViewModel.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fviewmodel%2Fdetails%2FGitHubUserDetailsViewModel.kt)|93.55%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/viewmodel/favourite/FavouriteUsersModel.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fviewmodel%2Ffavourite%2FFavouriteUsersModel.kt)|90.00%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/viewmodel/favourite/FavouriteUsersViewModel.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fviewmodel%2Ffavourite%2FFavouriteUsersViewModel.kt)|100.00%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/viewmodel/list/GitHubUserListModel.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fviewmodel%2Flist%2FGitHubUserListModel.kt)|78.95%|
|[shared/src/commonMain/kotlin/io/imrekaszab/githubuserfinder/viewmodel/list/GitHubUserListViewModel.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonMain%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2Fviewmodel%2Flist%2FGitHubUserListViewModel.kt)|58.54%|
|[shared/src/commonTest/kotlin/io/imrekaszab/githubuserfinder/FavouriteUsersViewModelTest.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonTest%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2FFavouriteUsersViewModelTest.kt)|100.00%|
|[shared/src/commonTest/kotlin/io/imrekaszab/githubuserfinder/GitHubUserDetailsViewModelTest.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonTest%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2FGitHubUserDetailsViewModelTest.kt)|100.00%|
|[shared/src/commonTest/kotlin/io/imrekaszab/githubuserfinder/GitHubUserListViewModelTest.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonTest%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2FGitHubUserListViewModelTest.kt)|100.00%|
|[shared/src/commonTest/kotlin/io/imrekaszab/githubuserfinder/GitHubUserServiceTest.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonTest%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2FGitHubUserServiceTest.kt)|100.00%|
|[shared/src/commonTest/kotlin/io/imrekaszab/githubuserfinder/MockHttpClient.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonTest%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2FMockHttpClient.kt)|85.37%|
|[shared/src/commonTest/kotlin/io/imrekaszab/githubuserfinder/MockModule.kt](https://github.com/kaszabimre/GitHubUserFinderKMM/blob/c9e58c50c892b41f8f5f4707b9e483109e897967/shared%2Fsrc%2FcommonTest%2Fkotlin%2Fio%2Fimrekaszab%2Fgithubuserfinder%2FMockModule.kt)|100.00%|

|Total Project Coverage|80.65%|
|:-|:-:|

_Originally posted by @kaszabimre in https://github.com/kaszabimre/GitHubUserFinderKMM/issues/17#issuecomment-1290603850_
           

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

