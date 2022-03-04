package io.lamart.aholdart.logic

import io.lamart.aholdart.optics.Source
import io.lamart.aholdart.rijksmuseum.RijksMuseum
import io.lamart.aholdart.services.Services
import kotlinx.coroutines.CoroutineScope

internal interface Dependencies : Services {
    val source: Source<State>
    val museum: RijksMuseum
    val scope: CoroutineScope
}

internal fun dependenciesOf(
    source: Source<State>,
    museum: RijksMuseum,
    services: Services,
    scope: CoroutineScope,
): Dependencies =
    object : Dependencies, Services by services {
        override val source: Source<State> = source
        override val museum: RijksMuseum = museum
        override val scope: CoroutineScope = scope
    }