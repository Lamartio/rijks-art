package io.lamart.rijksart

import com.liftric.kvault.KVault

actual class Platform : PlatformDependencies {
    private val vault = KVault()
    override val storage = Storage(vault)
}