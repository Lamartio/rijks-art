package io.lamart.rijksart.test

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.lamart.rijksart.logic.RijksState
import io.lamart.rijksart.network.ArtDetails
import io.lamart.rijksart.network.RijksMuseum
import io.lamart.rijksart.network.httpEngineFactory
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class RijksApi {
    lateinit var museum: RijksMuseum

    @BeforeTest
    fun setup() {
        museum = RijksMuseum()
    }

    @AfterTest
    fun teardown() {
        museum.httpClient.cancel()
        museum.httpClient.close()
    }

    @Test
    fun getCollection() = runTest {
        val result = museum.getCollection(0, 100)

        assertNotNull(result)
    }

    @Test
    fun getDetails() = runTest {
        val objectNumber = "SK-C-5" // the night's watch
        val y = museum.getDetails(objectNumber)

        assertNotNull(y)
    }
}
