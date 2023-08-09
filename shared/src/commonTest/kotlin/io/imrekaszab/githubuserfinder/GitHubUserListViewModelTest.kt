package io.imrekaszab.githubuserfinder

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser
import io.imrekaszab.githubuserfinder.service.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.service.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.viewmodel.list.GitHubUserListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.kodein.mock.Mock
import org.kodein.mock.tests.TestsWithMocks
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GitHubUserListViewModelTest : TestsWithMocks() {
    @Mock
    lateinit var action: GitHubUserAction

    @Mock
    lateinit var store: GitHubUserStore

    private val viewModel by withMocks { GitHubUserListViewModel(action, store) }

    override fun setUpMocks() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        injectMocks(mocker)
    }

    @AfterTest
    fun clear() {
        Dispatchers.resetMain()
        viewModel.clear()
    }

    @Test
    fun `given emptyList when loadUsers called then returns emptyList`() = runTest {
        // Given
        val emptyList = emptyList<GitHubUser>()
        every { store.getUsers() } returns flowOf(emptyList)
        every { store.isFetchingFinished() } returns flowOf(true)

        // When
        viewModel.loadUsers()

        // Then
        val result = viewModel.state.first()

        assertEquals(emptyList, result.data)

        verifyWithSuspend {
            viewModel.loadUsers()
            viewModel.setState(isAny())
        }
    }

    @Test
    fun `given null userName when searchUser called then returns emptyList`() = runTest {
        // Given
        val userName = null
        everySuspending { action.searchUser(isAny()) } returns Unit
        every { store.getUsers() } returns flowOf(emptyList())
        every { store.isFetchingFinished() } returns flowOf(true)

        // When
        viewModel.searchUser(userName)

        // Then
        val result = viewModel.state.first()

        assertTrue(result.data.isEmpty())

        verifyWithSuspend {
            viewModel.searchUser(isAny())
            viewModel.setState(isAny())
        }
    }

    @Test
    fun `given mock username when searchUser called then returns non-emptyList with the user`() = runTest {
        // Given
        val userName = MockData.userName
        everySuspending { action.searchUser(isAny()) } returns Unit
        every { store.getUsers() } returns flowOf(listOf(MockData.user))
        every { store.isFetchingFinished() } returns flowOf(true)

        // When
        viewModel.searchUser(userName)

        // Then
        val result = viewModel.state.first()

        assertEquals(userName, result.data.first().login)

        verifyWithSuspend {
            viewModel.searchUser(isAny())
            viewModel.setState(isAny())
        }
    }

    @Test
    fun `given mock user when requestNextPage called then returns non-emptyList with the user`() = runTest {
        // Given
        val userName = MockData.userName
        everySuspending { action.fetchNextPage() } returns Unit
        every { store.getUsers() } returns flowOf(listOf(MockData.user))
        every { store.isFetchingFinished() } returns flowOf(true)

        // When
        viewModel.requestNextPage()

        // Then
        val result = viewModel.state.first()

        assertEquals(userName, result.data.first().login)

        verifyWithSuspend {
            viewModel.requestNextPage()
            viewModel.setState(isAny())
        }
    }
}
