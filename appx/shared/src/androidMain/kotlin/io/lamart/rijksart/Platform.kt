package io.lamart.rijksart

import android.content.Context
import com.liftric.kvault.KVault

actual class Platform(context: Context): PlatformDependencies {
     actual override val vault = KVault(context)
}

