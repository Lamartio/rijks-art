package io.lamart.rijksart

import android.content.Context
import com.liftric.kvault.KVault

actual class Platform(context: Context): PlatformDependencies {
     private val vault = KVault(context)
     override val storage: Storage = Storage(vault)
}

