package io.imrekaszab.githubuserfinder

import io.imrekaszab.githubuserfinder.di.*
import io.imrekaszab.githubuserfinder.viewmodel.favourite.FavouriteUsersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FavouriteUsersViewModelTest {
    private val viewModel by lazy { FavouriteUsersViewModel() }

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
    fun `state gives back an empty list after deleteAllUser`() = runBlocking {
        // Given
        val listIsEmpty = true

        // When
        viewModel.deleteAllUser()

        // Then

        val state = viewModel.state.first()
        assertEquals(listIsEmpty, state.data.isEmpty())
    }
}
