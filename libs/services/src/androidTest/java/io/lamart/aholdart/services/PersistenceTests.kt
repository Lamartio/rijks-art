package io.lamart.aholdart.services

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.lamart.aholdart.domain.ArtDetails
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PersistenceTests {

    private val id = UUID.randomUUID().toString()
    private val details = ArtDetails(
        artObject = ArtDetails.ArtObject(
            id = id,
            labelText = "",
            language = "",
            links = ArtDetails.Links(search = ""),
            location = ""
        )
    )

    @Test
    fun persistDetails() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val services = servicesOf(context)

        services.setDetails(details)
        assertEquals(details, services.getDetails())
    }

}