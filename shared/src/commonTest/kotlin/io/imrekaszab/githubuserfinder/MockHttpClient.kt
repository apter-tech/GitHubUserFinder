package io.imrekaszab.githubuserfinder

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.encodedPath
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.http.hostWithPort
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private val Url.hostWithPortIfRequired: String
    get() = if (port == protocol.defaultPort) {
        host
    } else {
        hostWithPort
    }
private val Url.urlWithoutPath: String
    get() = "${protocol.name}://$hostWithPortIfRequired"
private val Url.fullUrl: String
    get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

internal val mockNetworkModule = module {
    single {
        mockHttpClient
    }
}

private val mockHttpClient =
    HttpClient(MockEngine) {
        defaultRequest {
            url.takeFrom(
                URLBuilder().takeFrom(MockData.baseUrl).apply {
                    encodedPath += url.encodedPath
                }
            )
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

        engine {
            addHandler { request ->
                when (request.url.urlWithoutPath) {
                    MockData.baseUrl -> {
                        when (request.method) {
                            HttpMethod.Get -> {
                                if (request.url.fullUrl.contains("search/users")) {
                                    respond(
                                        content = getSearchResponseContentByUrl(request.url.fullUrl),
                                        status = HttpStatusCode.OK,
                                        headers = headersOf("Content-Type", "application/json")
                                    )
                                } else if (request.url.fullUrl.contains("users/")) {
                                    respond(
                                        content = MockData.userDetailsResponse,
                                        status = HttpStatusCode.OK,
                                        headers = headersOf("Content-Type", "application/json")
                                    )
                                } else {
                                    respondError(HttpStatusCode.BadRequest)
                                }
                            }
                            else -> respondError(HttpStatusCode.Unauthorized)
                        }
                    }
                    else -> respondError(HttpStatusCode.NotFound)
                }
            }
        }
    }

private fun getSearchResponseContentByUrl(fullUrl: String) =
    if (fullUrl.contains("?q=&")) {
        MockData.emptyResponse
    } else {
        MockData.nonEmptyResponse
    }
