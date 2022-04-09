package io.imrekaszab.githubuserfinder.action

interface GitHubUserAction {
    @Throws(RuntimeException::class)
    suspend fun searchUser(userName: String)
}
