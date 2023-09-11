package io.lamart.rijksart.logic

import io.lamart.lux.Mutable
import io.lamart.rijksart.rijksmuseum.RijksMuseum
import io.lamart.rijksart.services.Services
import kotlinx.coroutines.CoroutineScope

internal interface Dependencies : Services {
    val source: Mutable<State>
    val museum: RijksMuseum
    val scope: CoroutineScope
}

internal fun dependenciesOf(
    source: Mutable<State>,
    museum: RijksMuseum,
    services: Services,
    scope: CoroutineScope,
): Dependencies =
    object : Dependencies, Services by services {
        override val source: Mutable<State> = source
        override val museum: RijksMuseum = museum
        override val scope: CoroutineScope = scope
    }