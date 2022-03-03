package io.lamart.aholdart.services

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
class ExampleInstrumentedTest {
    @Test
    fun persistData() = runBlocking {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val services = servicesOf(appContext)
        val data = UUID.randomUUID().toString()

        services.setDetails(data)
        assertEquals(data, services.getDetails())
    }

}