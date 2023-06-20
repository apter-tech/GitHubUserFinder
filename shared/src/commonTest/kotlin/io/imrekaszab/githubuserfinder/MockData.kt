package io.imrekaszab.githubuserfinder

import io.imrekaszab.githubuserfinder.model.domain.GitHubUser

object MockData {
    const val baseUrl = "https://api.github.com"
    const val userName = "example"
    const val emptyResponse = "{\"items\":[] }"
    const val userDetailsResponse = "{\n" +
        "\"login\": \"$userName\",\n" +
        "\"id\": 1,\n" +
        "\"avatar_url\": \"avatar_link\",\n" +
        "\"followers_url\": \"followers_link\",\n" +
        "\"repos_url\": \"repos_link\",\n" +
        "\"name\": \"name\",\n" +
        "\"company\": \"company\",\n" +
        "\"blog\": \"blog\",\n" +
        "\"location\": \"location\",\n" +
        "\"email\": \"email\",\n" +
        "\"bio\": \"bio\",\n" +
        "\"twitter_username\": \"twitter_username\",\n" +
        "\"score\": 1,\n" +
        "\"public_repos\": 1,\n" +
        "\"following\": 1,\n" +
        "\"followers\": 1,\n" +
        "\"following_url\": \"following_url\"\n" +
        "}\n"
    const val nonEmptyResponse = "{\n" +
        "\"total_count\": 1,\n" +
        "\"items\": [\n" + userDetailsResponse + "]\n" + "}"

    val user = GitHubUser(
        id = 1,
        login = userName,
        avatarUrl = "avatar_link",
        name = "name",
        company = "company",
        blog = "blog",
        location = "location",
        email = "email",
        bio = "bio",
        twitterUsername = "twitter_username",
        publicRepos = 1,
        following = 1,
        followers = 1
    )
}
