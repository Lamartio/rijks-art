package io.lamart.rijksart.logic

import io.lamart.lux.focus.FocusedLens
import io.lamart.rijksart.PlatformDependencies
import io.lamart.rijksart.network.RijksMuseum
import kotlinx.coroutines.CoroutineScope

internal interface RijksDepedencies: PlatformDependencies {
    val focus: FocusedLens<*, RijksState>
    val scope: CoroutineScope
    val museum: RijksMuseum
}