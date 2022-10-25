package io.imrekaszab.githubuserfinder

import app.cash.turbine.test
import io.imrekaszab.githubuserfinder.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.di.apiModule
import io.imrekaszab.githubuserfinder.di.coreModule
import io.imrekaszab.githubuserfinder.di.dataModule
import io.imrekaszab.githubuserfinder.di.factoryModule
import io.imrekaszab.githubuserfinder.di.platformModule
import io.imrekaszab.githubuserfinder.di.repositoryModule
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.store.GitHubUserStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GitHubUserServiceTest : KoinTest {

    private val action: GitHubUserAction by inject()
    private val store: GitHubUserStore by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                apiModule,
                repositoryModule,
                coreModule,
                platformModule,
                factoryModule,
                dataModule,
                mockModule
            )
        }
    }

    @AfterTest
    fun clear() {
        stopKoin()
    }

    @Test
    fun `store gives back an empty list after a search with empty string`() = runBlocking {
        // Given
        val emptyList = emptyList<GitHubUser>()

        // When
        action.searchUser("")
        val result = store.getUsers().first()

        // Then
        assertEquals(emptyList, result)
    }

    @Test
    fun `isFetchingFinished is true by default because the search has not been started yet`() =
        runBlocking {
            // Given
            val isFetchingFinished = true

            // When
            val result = store.isFetchingFinished().first()

            // Then
            assertEquals(isFetchingFinished, result)
        }

    @Test
    fun `store gives back a non empty list after search`() = runBlocking {
        // Given
        val listIsNotEmpty = true
        val userName = MockData.userName

        // When
        action.searchUser(userName)
        val result = store.getUsers().first()

        // Then
        assertEquals(listIsNotEmpty, result.isNotEmpty())
    }

    @Test
    fun `isFetchingFinished is true after search`() = runBlocking {
        // Given
        val isFetchingFinished = true
        val userName = MockData.userName

        // When
        action.searchUser(userName)
        val result = store.isFetchingFinished().first()

        // Then
        assertEquals(isFetchingFinished, result)
    }

    @Test
    fun `store gives back user detail after refresh`() = runBlocking {
        // Given
        val userName = MockData.userName

        // When
        action.refreshUserDetails(userName)

        // Then
        store.getUserDetails().test {
            assertEquals(userName, awaitItem()?.login)
        }
    }

    @Test
    fun `store gives back user after refresh and save`() = runBlocking {
        // Given
        val userName = MockData.userName

        // When
        action.refreshUserDetails(userName)
        action.saveUser()

        // Then
        store.getUserDetails().test {
            assertEquals(userName, awaitItem()?.login)
        }
    }

    @Test
    fun `store gives back emptyList after deleteAll`() = runBlocking {
        // Given
        val isEmpty = true

        // When
        action.deleteAllUser()
        val result = store.getSavedUsers().first()

        // Then
        assertEquals(isEmpty, result.isEmpty())
    }

    @Test
    fun `store gives back emptyList after save and delete`() = runBlocking {
        // Given
        val isEmpty = true
        val userName = "test"

        // When
        action.refreshUserDetails(userName)
        action.saveUser()
        action.deleteUser()
        val result = store.getSavedUsers().first()

        // Then
        assertEquals(isEmpty, result.isEmpty())
    }

    @Test
    fun `store gives back non-emptyList after save and refresh`() = runBlocking {
        // Given
        val isNonEmpty = true
        val userName = "test"

        // When
        action.refreshUserDetails(userName)
        action.saveUser()

        // Then
        store.getSavedUsers().test {
            assertEquals(isNonEmpty, awaitItem().isNotEmpty())
        }
    }
}
