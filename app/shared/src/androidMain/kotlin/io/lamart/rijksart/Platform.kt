package io.lamart.rijksart

import android.content.Context
import com.liftric.kvault.KVault
import io.ktor.client.engine.HttpClientEngineFactory
import io.lamart.rijksart.network.RijksMuseum
import io.lamart.rijksart.network.httpEngineFactory

actual class Platform(context: Context): PlatformDependencies {
     override val vault = KVault(context)
}

