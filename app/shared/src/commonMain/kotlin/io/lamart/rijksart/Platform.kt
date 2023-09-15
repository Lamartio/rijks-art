package io.lamart.rijksart

import com.liftric.kvault.KVault
import io.ktor.client.engine.HttpClientEngineFactory
import io.lamart.rijksart.network.RijksMuseum
import io.lamart.rijksart.network.httpEngineFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.newSingleThreadContext

/**
 * Needs to be instantiated in the app itself, so it can transport any platform specific dependencies (In the case of Android, it requires a Context).
 *
 * From an instance, shared logic can be instantiated. See `Rijksmachine` for that
 */

expect class Platform : PlatformDependencies

internal interface PlatformDependencies {
    val vault: KVault
}