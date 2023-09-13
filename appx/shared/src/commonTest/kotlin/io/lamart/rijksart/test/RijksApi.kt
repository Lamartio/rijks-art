package io.lamart.rijksart.test

import io.lamart.rijksart.network.RijksMuseum
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
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
    fun test() = runTest {
        val result = museum.getCollection(0)

        assertNotNull(result)
    }
}
