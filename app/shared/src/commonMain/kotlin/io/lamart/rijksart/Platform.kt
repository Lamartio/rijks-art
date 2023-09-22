package io.lamart.rijksart

/**
 * Needs to be instantiated in the app itself, so it can transport any platform specific dependencies (In the case of Android, it requires a Context).
 *
 * From an instance, shared logic can be instantiated. See `Rijksmachine` for that
 */

expect class Platform : PlatformDependencies

interface PlatformDependencies {
    val storage: Storage
}