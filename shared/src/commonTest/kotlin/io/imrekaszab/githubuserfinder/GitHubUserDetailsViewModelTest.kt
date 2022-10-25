package io.imrekaszab.githubuserfinder

import app.cash.turbine.test
import io.imrekaszab.githubuserfinder.di.apiModule
import io.imrekaszab.githubuserfinder.di.coreModule
import io.imrekaszab.githubuserfinder.di.dataModule
import io.imrekaszab.githubuserfinder.di.platformModule
import io.imrekaszab.githubuserfinder.di.repositoryModule
import io.imrekaszab.githubuserfinder.viewmodel.details.GitHubUserDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GitHubUserDetailsViewModelTest {
    private val viewModel by lazy { GitHubUserDetailsViewModel() }

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        startKoin {
            modules(
                apiModule,
                repositoryModule,
                coreModule,
                platformModule,
                dataModule,
                mockModule
            )
        }
    }

    @AfterTest
    fun clear() {
        Dispatchers.resetMain()
        viewModel.clear()
        stopKoin()
    }

    @Test
    fun `state gives back the correct user after a refresh with userName`() = runBlocking {
        // Given
        val userName = MockData.userName

        // When
        viewModel.refreshUserDetails(userName)

        // Then
        viewModel.state.filter { it.userDetails != null }.test {
            assertEquals(userName, awaitItem().userDetails?.login)
        }
    }

    @Test
    fun `state gives back user details as null after refresh and delete`() = runBlocking {
        // Given
        val userName = MockData.userName

        // When
        viewModel.refreshUserDetails(userName)
        viewModel.deleteUser()

        // Then
        viewModel.state.test {
            assertEquals(null, awaitItem().userDetails)
        }
    }

    @Test
    fun `state gives back user details after refresh and save`() = runBlocking {
        // Given
        val userName = MockData.userName

        // When
        viewModel.refreshUserDetails(userName)
        viewModel.saveUser()

        // Then
        viewModel.state.filter { it.userDetails != null }.test {
            assertEquals(userName, awaitItem().userDetails?.login)
        }
    }
}
