package io.lamart.rijksart

import com.liftric.kvault.KVault

internal interface PlatformDependencies {
    val vault: KVault
}

/**
 * Needs to be instantiated in the app itself, so it can transport any platform specific dependencies (In the case of Android, it requires a Context).
 *
 * From an instance, shared logic can be instantiated. For now that is only a machine.
 */

expect class Platform : PlatformDependencies {
    override val vault: KVault
}
