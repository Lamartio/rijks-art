package io.lamart.rijksart.logic

import io.lamart.lux.Machine
import io.lamart.lux.Mutable
import io.lamart.rijksart.Platform
import io.lamart.rijksart.PlatformDependencies
import io.lamart.rijksart.logic.details.DetailsMachine
import io.lamart.rijksart.logic.gallery.GalleryMachine
import io.lamart.rijksart.logic.gallery.toOverviewState
import io.lamart.rijksart.network.RijksMuseum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow

fun Platform.toMachine(): RijksMachine = RijksMachine(this)

class RijksMachine internal constructor(platform: Platform) :
    Machine<RijksState, RijksActions>(initialize(platform)) {
    val gallery = this
        .compose(
            state = RijksState::toOverviewState,
            actions = RijksActions::gallery
        )
        .let(::GalleryMachine)
    val details = this
        .compose(
            state = RijksState::details,
            actions = RijksActions::details
        )
        .let(::DetailsMachine)
}

private fun initialize(platform: PlatformDependencies): Machine<RijksState, RijksActions> {
    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    val state = MutableStateFlow(RijksState())
    val mutable = Mutable(get = state::value, set = { state.value = it })
    val deps = object : RijksDepedencies, PlatformDependencies by platform {
        override val focus = mutable.lens
        override val scope = scope
        override val museum = RijksMuseum()
    }
    val actions = RijksActions(deps)

    return Machine(scope, state, actions)
}
