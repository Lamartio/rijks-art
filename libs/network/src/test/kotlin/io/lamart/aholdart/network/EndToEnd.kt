package io.lamart.aholdart.network

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


class EndToEnd {

    lateinit var rijksMuseum: RijksMuseum

    @Before
    fun setup() {
        rijksMuseum = RijksMuseum(key = "0fiuZFh4")
    }

    @Test
    fun getCollection() = runBlocking {
        val collection = rijksMuseum.getCollection()

        assertNotNull(collection.artObjects)
    }

    @Test
    fun getDetails() = runBlocking {
        val collection = rijksMuseum.getCollection()
        val objectNumber = collection.artObjects.first().objectNumber
        val details = rijksMuseum.getDetails(objectNumber)

        assertNotNull(details)
    }
}