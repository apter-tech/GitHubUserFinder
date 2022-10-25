package io.imrekaszab.githubuserfinder

import app.cash.turbine.test
import io.imrekaszab.githubuserfinder.di.apiModule
import io.imrekaszab.githubuserfinder.di.coreModule
import io.imrekaszab.githubuserfinder.di.dataModule
import io.imrekaszab.githubuserfinder.di.platformModule
import io.imrekaszab.githubuserfinder.di.repositoryModule
import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.viewmodel.list.GitHubUserListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

class GitHubUserListViewModelTest {
    private val viewModel by lazy { GitHubUserListViewModel() }

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
    fun `state gives back an empty list after a search with empty string`() = runBlocking {
        // Given
        val emptyList = emptyList<GitHubUser>()

        // When
        viewModel.searchUser("")
        delay(200)

        // Then
        viewModel.state.filter { it.data.isEmpty() }.test {
            assertEquals(emptyList, awaitItem().data)
        }
    }

    @Test
    fun `state gives back a non empty list after search`() = runBlocking {
        // Given
        val listIsNotEmpty = true
        val userName = MockData.userName

        // When
        viewModel.searchUser(userName)

        // Then
        viewModel.state.filter { it.data.isNotEmpty() }.test {
            assertEquals(awaitItem().data.isNotEmpty(), listIsNotEmpty)
        }
    }
}
