package io.lamart.rijksart

import com.liftric.kvault.KVault

actual class Platform : PlatformDependencies {
    override val vault = KVault()
}