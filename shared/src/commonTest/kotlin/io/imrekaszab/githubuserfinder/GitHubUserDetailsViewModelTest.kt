package io.imrekaszab.githubuserfinder

import io.imrekaszab.githubuserfinder.service.action.GitHubUserAction
import io.imrekaszab.githubuserfinder.service.store.GitHubUserStore
import io.imrekaszab.githubuserfinder.viewmodel.details.GitHubUserDetailsViewModel
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GitHubUserDetailsViewModelTest : TestsWithMocks() {

    @Mock
    lateinit var store: GitHubUserStore

    @Mock
    lateinit var action: GitHubUserAction

    private val viewModel by withMocks { GitHubUserDetailsViewModel(action, store) }

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
    fun `given mock user when saveUser called then returns the user with favourite true`() = runTest {
        // Given
        val user = MockData.user.copy(favourite = true)
        every { store.getUserDetails() } returns flowOf(user)
        everySuspending { action.saveUser() } returns Unit

        // When
        viewModel.saveUser()

        // Then
        val result = viewModel.state.first()

        assertTrue(result.userDetails?.favourite == true)

        verifyWithSuspend {
            viewModel.saveUser()
            viewModel.setState(isAny())
        }
    }

    @Test
    fun `given mock user when refreshUserDetails called then returns the user`() = runTest {
        // Given
        val user = MockData.user
        every { store.getUserDetails() } returns flowOf(user)
        everySuspending { action.refreshUserDetails(isAny()) } returns Unit

        // When
        viewModel.refreshUserDetails(user.name)

        // Then
        val result = viewModel.state.first()

        assertEquals(user, result.userDetails)

        verifyWithSuspend {
            viewModel.refreshUserDetails(isAny())
            viewModel.setState(isAny())
        }
    }

    @Test
    fun `given mock user when deleteUser called then returns the user with favourite false`() = runTest {
        // Given
        val user = MockData.user.copy(favourite = false)
        every { store.getUserDetails() } returns flowOf(user)
        everySuspending { action.deleteUser() } returns Unit

        // When
        viewModel.deleteUser()

        // Then
        val result = viewModel.state.first()

        assertFalse(result.userDetails?.favourite == true)

        verifyWithSuspend {
            viewModel.deleteUser()
            viewModel.setState(isAny())
        }
    }
}
