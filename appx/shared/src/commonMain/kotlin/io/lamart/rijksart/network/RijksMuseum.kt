package io.lamart.rijksart.network

import ArtCollection
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RijksMuseum(httpFactory: HttpClientEngineFactory<*> = httpEngineFactory) {

    internal val client: HttpClient = httpClientOf(httpFactory)

    suspend fun getCollection(
        page: Int,
        pageSize: Int? = null,
        sort: String? = null
    ): ArtCollection =
        client
            .get("collection") {
                parameter("p", page)
                pageSize?.let { parameter("ps", it) }
                sort?.let { parameter("s", it) }
            }
            .body()

    suspend fun getDetails(objectNumber: String): ArtDetails =
        client
            .get("collection/${objectNumber}")
            .body()

    companion object
}

private fun httpClientOf(httpFactory: HttpClientEngineFactory<*>): HttpClient =
    HttpClient(httpFactory) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        defaultRequest {
            url(
                scheme = "https",
                host = "www.rijksmuseum.nl",
                path = "api/en/"
            ) {
                contentType(ContentType.Application.Json)
                parameters["key"] = "0fiuZFh4"
            }
        }
    }
