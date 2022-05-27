package io.imrekaszab.githubuserfinder.action

interface GitHubUserAction {
    @Throws(RuntimeException::class)
    suspend fun searchUser(userName: String)

    @Throws(RuntimeException::class)
    suspend fun fetchNextPage()

    @Throws(RuntimeException::class)
    suspend fun refreshUserDetails(userName: String)
}
