package io.lamart.rijksart.logic

import io.lamart.lux.focus.FocusedLens
import io.lamart.rijksart.PlatformDependencies
import io.lamart.rijksart.network.RijksMuseum
import kotlinx.coroutines.CoroutineScope

/**
 * Contains all the dependencies necessary for the logic of the application.
 *
 * It is represented as an interface, so it can be used by [class delegation](https://kotlinlang.org/docs/delegation.html#overriding-a-member-of-an-interface-implemented-by-delegation).
 */

internal interface RijksDepedencies: PlatformDependencies {
    val focus: FocusedLens<*, RijksState>
    val scope: CoroutineScope
    val museum: RijksMuseum
}