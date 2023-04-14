package io.imrekaszab.githubuserfinder.service.action

interface GitHubUserAction {
    suspend fun searchUser(userName: String)
    suspend fun fetchNextPage()
    suspend fun refreshUserDetails(userName: String)
    suspend fun saveUser()
    suspend fun deleteUser()
    suspend fun deleteAllUser()
}
