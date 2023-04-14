package io.imrekaszab.githubuserfinder

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.service.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.service.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.viewmodel.favourite.FavouriteUsersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.kodein.mock.Mock
import org.kodein.mock.tests.TestsWithMocks
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FavouriteUsersViewModelTest : TestsWithMocks() {
    @Mock
    lateinit var action: GitHubUserAction

    @Mock
    lateinit var store: GitHubUserStore

    private val viewModel by withMocks { FavouriteUsersViewModel(action, store) }

    override fun setUpMocks() = injectMocks(mocker)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @AfterTest
    fun clear() {
        Dispatchers.resetMain()
        viewModel.clear()
    }

    @Test
    fun `given non-emptyList when loadUsers called then returns non-emptyList`() = runTest {
        // Given
        val userList = listOf(MockData.user)
        every { store.getSavedUsers() } returns flowOf(userList)

        // When
        viewModel.loadUsers()

        // Then
        val result = viewModel.state.first()

        assertEquals(userList, result.data)

        verifyWithSuspend {
            viewModel.loadUsers()
            viewModel.setState(isAny())
        }
    }

    @Test
    fun `given emptyList when deleteAllUser called then returns emptyList`() = runTest {
        // Given
        val userList = emptyList<GitHubUser>()
        everySuspending { action.deleteAllUser() } returns Unit
        every { store.getSavedUsers() } returns flowOf(userList)

        // When
        viewModel.deleteAllUser()

        // Then
        val result = viewModel.state.first()

        assertEquals(userList, result.data)

        verifyWithSuspend {
            viewModel.deleteAllUser()
            viewModel.setState(isAny())
        }
    }
}
