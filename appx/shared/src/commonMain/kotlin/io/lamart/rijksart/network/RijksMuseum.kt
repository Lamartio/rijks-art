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
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlin.time.DurationUnit
import kotlin.time.toDuration

interface RijksMuseum {

    val httpClient: HttpClient

    suspend fun getCollection(
        page: Int,
        pageSize: Int? = null,
        sort: String? = null
    ): ArtCollection =
        httpClient
            .get("collection") {
                parameter("p", page)
                pageSize?.let { parameter("ps", it) }
                sort?.let { parameter("s", it) }
            }
            .body()

//    suspend fun getDetails(objectNumber: String): ArtDetails

    companion object : () -> RijksMuseum {
        override operator fun invoke(): RijksMuseum =
            object : RijksMuseum {
                override val httpClient = httpFactory.toClient()
            }
    }
}

private fun HttpClientEngineFactory<*>.toClient() =
    HttpClient(this) {
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
