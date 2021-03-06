package io.lamart.rijksart.logic

import io.lamart.rijksart.optics.sourceOf
import io.lamart.rijksart.rijksmuseum.RijksMuseum
import io.lamart.rijksart.services.Services
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface Logic {

    val state: Flow<State>

    val actions: Actions

}

fun logicOf(museum: RijksMuseum, services: Services, scope: CoroutineScope): Logic {
    val state = MutableStateFlow(State())
    val source = sourceOf(
        get = { state.value },
        set = state::tryEmit
    )
    val dependencies = dependenciesOf(source, museum, services, scope)

    return object : Logic {
        override val state: Flow<State> = state
        override val actions: Actions = dependencies.toActions()
    }
}