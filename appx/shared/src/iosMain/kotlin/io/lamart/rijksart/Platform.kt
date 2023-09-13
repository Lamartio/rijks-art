package io.lamart.rijksart

import com.liftric.kvault.KVault

actual class Platform : PlatformDependencies {
    actual override val vault = KVault()
}